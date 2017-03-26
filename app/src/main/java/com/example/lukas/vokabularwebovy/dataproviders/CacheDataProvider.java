package com.example.lukas.vokabularwebovy.dataproviders;

import android.util.LruCache;

/**
 * Created by lukas on 26.03.2017.
 */
public class CacheDataProvider <K, V>  {
    private LruCache<K, V> memoryCache;

    public CacheDataProvider() {
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int cacheSize = maxMemory / 10;
        memoryCache = new LruCache<>(cacheSize);
    }

    public void addItem(K key, V value){
        if(getItem(key) == null)memoryCache.put(key, value);
    }

    public V getItem(K key){
       return memoryCache.get(key);
    }
}
