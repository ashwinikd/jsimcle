package com.ashwinikd.jsimcl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import com.ashwinikd.jsimcl.CacheValue;
import junit.framework.TestCase;

public class CacheValueTest extends TestCase
{
    protected void setUp() throws Exception {
        super.setUp();
    }

    /**
     * test set without ttl
     */
    public void testCacheValueObject() {
        List<Integer> obj = new ArrayList<Integer>();
        Random rand = new Random();
        for(int i = 0; i < 10; i++) {
            obj.add(rand.nextInt(2000));
        }
        CacheValue cv = new CacheValue(obj);
        assertNotNull(cv.getValue());
        assertTrue(cv.getValue().equals(obj));
        assertFalse(cv.hasExpired());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
        assertFalse(cv.hasExpired());
    }

    /**
     * test with ttl
     */
    public void testCacheValueObjectLong() {
        List<Integer> obj = new ArrayList<Integer>();
        Random rand = new Random();
        for(int i = 0; i < 10; i++) {
            obj.add(rand.nextInt(2000));
        }
        CacheValue cv = new CacheValue(obj, 5);
        assertNotNull(cv.getValue());
        assertTrue(cv.getValue().equals(obj));
        assertFalse(cv.hasExpired());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
        assertFalse(cv.hasExpired());
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
        assertTrue(cv.hasExpired());
    }
}
