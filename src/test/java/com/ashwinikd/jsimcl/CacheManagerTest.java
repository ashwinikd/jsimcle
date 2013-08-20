package com.ashwinikd.jsimcl;

import com.ashwinikd.jsimcl.CacheManager;
import junit.framework.TestCase;

public class CacheManagerTest extends TestCase
{
    private CacheManager cache;
    
    protected void setUp() throws Exception {
        super.setUp();
        cache = CacheManager.getInstance();
    }

    /**
     * test non-ttl set
     */
    public void testPutStringObject() {
        String key1 = "cacheKey-1";
        String val1 = "May your mountain dew be sweet";
        String key2 = "cacheKey-2";
        String val2 = "May cage's words script your glory.";
        
        cache.put(key1, val1);
        cache.put(key2, val2);
        assertNotNull(cache.get(key1));
        assertEquals(val1, cache.get(key1));
        assertNotNull(cache.get(key2));
        assertEquals(val2, cache.get(key2));
        
        cache.put(key1, val2);
        assertNotNull(cache.get(key1));
        assertEquals(val2, cache.get(key1));
    }

    /**
     * test set with ttl
     */
    public void testPutStringObjectLong() {
        String key1 = "cacheKey-1";
        String val1 = "May your mountain dew be sweet";
        
        cache.put(key1, val1, 2);
        assertNotNull(cache.get(key1));
        assertEquals(val1, cache.get(key1));
        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
        assertNotNull(cache.get(key1));
        assertEquals(val1, cache.get(key1));

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
        assertNull(cache.get(key1));
    }

    /**
     * test clear by key method
     */
    public void testClearString() {
        String key1 = "cacheKey-1";
        String val1 = "May your mountain dew be sweet";
        String key2 = "cacheKey-2";
        String val2 = "May cage's words script your glory.";

        cache.put(key1, val1);
        cache.put(key2, val2);
        assertNotNull(cache.get(key1));
        assertNotNull(cache.get(key2));
        cache.clear(key1);
        assertNull(cache.get(key1));
        assertNotNull(cache.get(key2));
        
        cache.put(key1, val1, 2);
        cache.put(key2, val2, 2);
        assertNotNull(cache.get(key1));
        assertNotNull(cache.get(key2));

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
        cache.clear(key1);
        assertNull(cache.get(key1));
        assertNotNull(cache.get(key2));
    }

    /**
     * test clear all method
     */
    public void testClear() {
        String key1 = "cacheKey-1";
        String val1 = "May your mountain dew be sweet";
        String key2 = "cacheKey-2";
        String val2 = "May cage's words script your glory.";

        cache.put(key1, val1);
        cache.put(key2, val2);
        assertNotNull(cache.get(key1));
        assertNotNull(cache.get(key2));
        cache.clear();
        assertNull(cache.get(key1));
        assertNull(cache.get(key2));
        
        cache.put(key1, val1, 2);
        cache.put(key2, val2, 2);
        assertNotNull(cache.get(key1));
        assertNotNull(cache.get(key2));

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
        cache.clear();
        assertNull(cache.get(key1));
        assertNull(cache.get(key2));
    }
}
