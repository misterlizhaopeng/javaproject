package com.lp;

import java.util.concurrent.ConcurrentHashMap;

public class MyCh extends ConcurrentHashMap{
    @Override
    public Object put(Object key, Object value) {
        key="lp_"+key;
        return super.put(key, value);
    }
}
