package com.ashwinikd.jsimcl;

import java.util.Date;

public class CacheValue
{
    private Object value;
    private long   createdOn;
    private long   timeToLive;
    
    /**
     * stores obj with no ttl
     * @param obj object to store
     */
    public CacheValue(Object obj) {
        timeToLive = 0;
        Date dt = new Date();
        createdOn = dt.getTime();
        value = obj;
    }
    
    /**
     * @param val object to store
     * @param ttl time to live
     */
    public CacheValue(Object val, long ttl) {
        value = val;
        timeToLive = ttl * 1000;
        Date dt = new Date();
        createdOn = dt.getTime();
    }

    /**
     * @return value stored
     */
    public Object getValue() {
        return value;
    }
    
    /**
     * @return true if cache has expired, false otherwise
     */
    public boolean hasExpired() {
        Date dt = new Date();
        return (timeToLive !=0 && dt.getTime() >= timeToLive + createdOn);
    }
}

