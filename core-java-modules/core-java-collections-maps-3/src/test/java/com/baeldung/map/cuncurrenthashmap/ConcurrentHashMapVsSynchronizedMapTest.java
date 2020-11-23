package com.baeldung.map.cuncurrenthashmap;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;

public class ConcurrentHashMapVsSynchronizedMapTest {
    
    public final static int THREAD_POOL_SIZE = 5;
    public final static int TEST_ITERATIONS = 5;
    public final static int TEST_NO_ITEMS = 500000;
    
    @Test
    public void randomReadAndWritePerformaceTest_cuncurrentHashMap_faster()
            throws InterruptedException {
        // For synchronizedMap
        Long totalTimeForSynchronizedMap = 0l;
        Map<String, Integer> slowerMap = Collections
                .synchronizedMap(new HashMap<String, Integer>());
        for (int i = 0; i < TEST_ITERATIONS; i++) {
            totalTimeForSynchronizedMap += performReadAndWriteTest(slowerMap);
        }
        Long avgTimeForSynchronizedMap = totalTimeForSynchronizedMap / TEST_ITERATIONS;
        
        // For ConcurrentHashMap Object
        Long totalTimeForCuncurrentHashMap = 0l;
        Map<String, Integer> fasterMap = new ConcurrentHashMap<>();
        for (int i = 0; i < TEST_ITERATIONS; i++) {
            totalTimeForCuncurrentHashMap += performReadAndWriteTest(fasterMap);
        }
        Long avgTimeForCuncurrentHashMap = totalTimeForCuncurrentHashMap / TEST_ITERATIONS;
        
        Assert.assertTrue(avgTimeForSynchronizedMap > avgTimeForCuncurrentHashMap);
    }
    
    @Test
    public void randomWritePerformaceTest_cuncurrentHashMap_faster() throws InterruptedException {
        // For synchronizedMap
        Long totalTimeForSynchronizedMap = 0l;
        Map<String, Integer> slowerMap = Collections
                .synchronizedMap(new HashMap<String, Integer>());
        for (int i = 0; i < TEST_ITERATIONS; i++) {
            totalTimeForSynchronizedMap += performWriteTest(slowerMap);
        }
        Long avgTimeForSynchronizedMap = totalTimeForSynchronizedMap / TEST_ITERATIONS;
        
        // For ConcurrentHashMap Object
        Long totalTimeForCuncurrentHashMap = 0l;
        Map<String, Integer> fasterMap = new ConcurrentHashMap<>();
        for (int i = 0; i < TEST_ITERATIONS; i++) {
            totalTimeForCuncurrentHashMap += performWriteTest(fasterMap);
        }
        Long avgTimeForCuncurrentHashMap = totalTimeForCuncurrentHashMap / TEST_ITERATIONS;
        
        Assert.assertTrue(avgTimeForSynchronizedMap > avgTimeForCuncurrentHashMap);
    }
    
    @Test
    public void randomReadPerformaceTest_cuncurrentHashMap_faster() throws InterruptedException {
        
        Map<String, Integer> slowerMap = Collections
                .synchronizedMap(addItems(new HashMap<String, Integer>()));
        // For synchronizedMap
        Long totalTimeForSynchronizedMap = 0l;
        for (int i = 0; i < TEST_ITERATIONS; i++) {
            totalTimeForSynchronizedMap += performReadTest(slowerMap);
        }
        Long avgTimeForSynchronizedMap = totalTimeForSynchronizedMap / TEST_ITERATIONS;
        
        Map<String, Integer> fasterMap = Collections
                .synchronizedMap(addItems(new ConcurrentHashMap<String, Integer>()));
        // For ConcurrentHashMap Object
        Long totalTimeForCuncurrentHashMap = 0l;
        new ConcurrentHashMap<>();
        for (int i = 0; i < TEST_ITERATIONS; i++) {
            totalTimeForCuncurrentHashMap += performReadTest(fasterMap);
        }
        Long avgTimeForCuncurrentHashMap = totalTimeForCuncurrentHashMap / TEST_ITERATIONS;
        
        Assert.assertTrue(avgTimeForSynchronizedMap > avgTimeForCuncurrentHashMap);
    }
    
    private Map<String, Integer> addItems(Map<String, Integer> map) {
        for (int i = 0; i < TEST_NO_ITEMS; i++) {
            Integer randNumber = (int) Math.ceil(Math.random() * TEST_NO_ITEMS);
            map.put(String.valueOf(randNumber), randNumber);
        }
        return map;
    }
    
    private long performWriteTest(final Map<String, Integer> map) throws InterruptedException {
        long startTime = System.nanoTime();
        ExecutorService exectures = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        for (int j = 0; j < THREAD_POOL_SIZE; j++) {
            exectures.execute(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < TEST_NO_ITEMS; i++) {
                        Integer randNumber = (int) Math.ceil(Math.random() * TEST_NO_ITEMS);
                        map.put(String.valueOf(randNumber), randNumber);
                    }
                }
            });
        }
        exectures.shutdown();
        exectures.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
        long entTime = System.nanoTime();
        return (entTime - startTime) / 1000000L;
    }
    
    private long performReadAndWriteTest(final Map<String, Integer> map)
            throws InterruptedException {
        long startTime = System.nanoTime();
        ExecutorService exectures = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        for (int j = 0; j < THREAD_POOL_SIZE; j++) {
            exectures.execute(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < TEST_NO_ITEMS; i++) {
                        Integer randNumber = (int) Math.ceil(Math.random() * TEST_NO_ITEMS);
                        Integer value = map.get(String.valueOf(randNumber));
                        map.put(String.valueOf(randNumber), randNumber);
                    }
                }
            });
        }
        exectures.shutdown();
        exectures.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
        long entTime = System.nanoTime();
        return (entTime - startTime) / 1000000L;
    }
    
    private long performReadTest(final Map<String, Integer> map) throws InterruptedException {
        long startTime = System.nanoTime();
        ExecutorService exectures = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        for (int j = 0; j < THREAD_POOL_SIZE; j++) {
            exectures.execute(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < TEST_NO_ITEMS; i++) {
                        Integer randNumber = (int) Math.ceil(Math.random() * TEST_NO_ITEMS);
                        Integer value = map.get(String.valueOf(randNumber));
                    }
                }
            });
        }
        exectures.shutdown();
        exectures.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
        long entTime = System.nanoTime();
        return (entTime - startTime) / 1000000L;
    }
}