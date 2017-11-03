package com.baeldung.concurrent.executorservice;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

import static junit.framework.TestCase.assertTrue;

public class WaitingForThreadsToFinishTest {

    private static final Logger LOG = LoggerFactory.getLogger(WaitingForThreadsToFinishTest.class);
    private final static ExecutorService WORKER_THREAD_POOL = Executors.newFixedThreadPool(10);

    @Test
    public void givenMultipleThreads_whenInvokeAll_thenMainThreadShouldWaitForAllToFinish() {

        ExecutorService WORKER_THREAD_POOL = Executors.newFixedThreadPool(10);

        List<Callable<String>> callables = Arrays.asList(new DelayedCallable("fast thread", 100), new DelayedCallable("slow thread", 3000));
        
        try {
            long startProcessingTime = System.currentTimeMillis();
            List<Future<String>> futures = WORKER_THREAD_POOL.invokeAll(callables);

            long totalProcessingTime = System.currentTimeMillis() - startProcessingTime;
            assertTrue(totalProcessingTime >= 3000);

            String firstThreadResponse = futures.get(0)
                .get();
            assertTrue("First response should be from the fast thread", "fast thread".equals(firstThreadResponse));

            String secondThreadResponse = futures.get(1)
                .get();
            assertTrue("Last response should be from the slow thread", "slow thread".equals(secondThreadResponse));

        } catch (ExecutionException | InterruptedException ex) {
            ex.printStackTrace();
        }

        WORKER_THREAD_POOL.shutdown();
    }

    @Test
    public void givenMultipleThreads_whenUsingCompletionService_thenMainThreadShouldWaitForAllToFinish() {

        CompletionService<String> service = new ExecutorCompletionService<>(WORKER_THREAD_POOL);

        List<Callable<String>> callables = Arrays.asList(new DelayedCallable("fast thread", 100), new DelayedCallable("slow thread", 3000));

        for (Callable<String> callable : callables) {
            service.submit(callable);
        }

        WORKER_THREAD_POOL.shutdown();

        try {

            long startProcessingTime = System.currentTimeMillis();

            Future<String> future = service.take();
            String firstThreadResponse = future.get();
            long totalProcessingTime = System.currentTimeMillis() - startProcessingTime;

            assertTrue("First response should be from the fast thread", "fast thread".equals(firstThreadResponse));
            assertTrue(totalProcessingTime >= 100 && totalProcessingTime < 1000);
            LOG.debug("Thread finished after: " + totalProcessingTime + " milliseconds");

            future = service.take();
            String secondThreadResponse = future.get();
            totalProcessingTime = System.currentTimeMillis() - startProcessingTime;

            assertTrue("Last response should be from the slow thread", "slow thread".equals(secondThreadResponse));
            assertTrue(totalProcessingTime >= 3000 && totalProcessingTime < 4000);
            LOG.debug("Thread finished after: " + totalProcessingTime + " milliseconds");

        } catch (ExecutionException | InterruptedException ex) {
            ex.printStackTrace();
        }

    }

    @Test
    public void givenMultipleThreads_whenUsingCompletableFutures_thenMainThreadShouldWaitForAllToFinish() {

        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return "Hello";
        });

        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return "Beautiful";
        });

        CompletableFuture<String> future3 = CompletableFuture.supplyAsync(() -> {

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return "World";
        });

        long startProcessingTime = System.currentTimeMillis();
        CompletableFuture<Void> combinedFuture = CompletableFuture.allOf(future1, future2, future3);
        combinedFuture.join();

        long totalProcessingTime = System.currentTimeMillis() - startProcessingTime;
        assertTrue(totalProcessingTime >= 5000 && totalProcessingTime < 6000);

        LOG.debug("Responses from all threads are available after " + totalProcessingTime + " milliseconds");

        try {
            String thread1Response = future1.get();
            assertTrue(thread1Response.equals("Hello"));

            String thread2Response = future2.get();
            assertTrue(thread2Response.equals("Beautiful"));

            String thread3Response = future3.get();
            assertTrue(thread3Response.equals("World"));

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        WORKER_THREAD_POOL.shutdown();
    }
}
