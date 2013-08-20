package com.ashwinikd.jsincl;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.apache.commons.math.stat.descriptive.SummaryStatistics;
import org.apache.commons.math.stat.descriptive.rank.Median;
import junit.framework.TestCase;

public class ConcurrencyTest extends TestCase
{
    private static final int NUM_THREADS = 500;
    private static final int POOL_SIZE = 40;
    private static final int NUM_OPN = 10000;
    private static final int MAX_KEY = 100;
    private static final int MAX_VAL = 100000;
    
    
    protected void setUp() throws Exception {
        super.setUp();
    }
    
    class CacheRunner implements Runnable {
        private int id;
        private Date dt;
        private Random rand = new Random();
        private CacheManager cache;
        private Stats[] stats = new Stats[3];
        private int numConflicts;
        
        public CacheRunner(int sid) {
            id = sid;
        }

        public void run() {
            cache = getCache();
            testWrites();
            testReads();
            testReadWrite();
            System.out.println("Finished run#" + id + "\n" +
                "Writes: " + stats[0] + "\n" +
                "Reads: " + stats[1] + "\n" +
                "R/W: " + stats[2] + ". Conflicts: " + numConflicts);
        }
        
        private void testWrites() {
            String key;
            int val;
            long start;
            long end;
            double[] time = new double[NUM_OPN];
            for(int i = 0; i < NUM_OPN; i++) {
                start = getTime();
                 key = String.valueOf(rand.nextInt(MAX_KEY));
                 val = rand.nextInt(MAX_VAL);
                 cache.put(key, val);
                 end = getTime();
                 time[i] = end - start;
            }
            stats[0] = new Stats(time);
        }
        
        private void testReads() {
            String key;
            long start;
            long end;
            double[] time = new double[NUM_OPN];
            for(int i = 0; i < NUM_OPN; i++) {
                start = getTime();
                 key = String.valueOf(rand.nextInt(MAX_KEY));
                 cache.get(key);
                 end = getTime();
                 time[i] = end - start;
            }
            stats[1] = new Stats(time);
        }

        private void testReadWrite() {
            String key;
            int val;
            long start;
            long end;
            double[] time = new double[NUM_OPN];
            for(int i = 0; i < NUM_OPN; i++) {
                start = getTime();
                 key = String.valueOf(rand.nextInt(MAX_KEY));
                 val = rand.nextInt(MAX_VAL);
                 cache.put(key, val);
                 if((Integer) cache.get(key) != val) {
                     numConflicts++;
                 }
                 end = getTime();
                 time[i] = end - start;
            }
            stats[2] = new Stats(time);
        }
        
        public long getTime() {
            dt = new Date();
            return dt.getTime();
        }
        
        private class Stats {
            public double min;
            public double max;
            public double avg;
            public double sdev;
            public double med;
            
            public Stats(double[] data) {
                SummaryStatistics stats = new SummaryStatistics();
                for(double d: data) stats.addValue(d);
                avg = stats.getMean();
                max = stats.getMax();
                min = stats.getMin();
                sdev = stats.getStandardDeviation();
                Median median = new Median();
                med = median.evaluate(data);
            }
            
            @Override
            public String toString() {
                return "Mean: " + avg + " +-" + sdev + " | Max: " + max + " | Min: " + min + " | Median: " + med;
            }
        }
    }
    
    /**
     * test concurrency
     * @throws InterruptedException 
     */
    public void testConcurrency() throws InterruptedException {
        ExecutorService cacheService = Executors.newFixedThreadPool(POOL_SIZE);
        
        for(int i = 0; i < NUM_THREADS; i++) {
            cacheService.submit(new CacheRunner(i));
        }
        cacheService.awaitTermination(20, TimeUnit.SECONDS);
        cacheService.shutdown();
    }
    
    private CacheManager getCache() {
        return CacheManager.getInstance();
    }
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("Starting concurrency test on cache manager.");
        System.out.println("NUM_THREADS = " + NUM_THREADS);
        System.out.println("NUM_OPN = " + NUM_OPN);
        System.out.println("POOL_SIZE = " + POOL_SIZE);
        ConcurrencyTest test = new ConcurrencyTest();
        try {
            test.testConcurrency();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
