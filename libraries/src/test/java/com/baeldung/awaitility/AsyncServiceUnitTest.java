package com.baeldung.awaitility;

import static org.awaitility.Awaitility.await;
import static org.awaitility.Awaitility.fieldIn;
import static org.awaitility.Awaitility.given;
import static org.awaitility.proxy.AwaitilityClassProxy.to;
import static org.hamcrest.Matchers.equalTo;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import org.awaitility.Awaitility;
import org.awaitility.Duration;
import org.junit.Before;
import org.junit.Test;

public class AsyncServiceUnitTest {
    private AsyncService asyncService;

    @Before
    public void setUp() {
        asyncService = new AsyncService();
    }

    @Test
    public void givenAsyncService_whenInitialize_thenInitOccurs1() {
        asyncService.initialize();
        Callable<Boolean> isInitialized = () -> asyncService.isInitialized();
        await().until(isInitialized);
    }

    @Test
    public void givenAsyncService_whenInitialize_thenInitOccurs2() {
        asyncService.initialize();
        Callable<Boolean> isInitialized = () -> asyncService.isInitialized();
        await().atLeast(Duration.ONE_HUNDRED_MILLISECONDS)
                .atMost(Duration.FIVE_SECONDS)
                .with().pollInterval(Duration.ONE_HUNDRED_MILLISECONDS)
                .until(isInitialized);
    }

    @Test
    public void givenAsyncService_whenInitialize_thenInitOccurs_withDefualts() {
        Awaitility.setDefaultPollInterval(10, TimeUnit.MILLISECONDS);
        Awaitility.setDefaultPollDelay(Duration.ZERO);
        Awaitility.setDefaultTimeout(Duration.ONE_MINUTE);

        asyncService.initialize();
        await().until(() -> asyncService.isInitialized());
    }

    @Test
    public void givenAsyncService_whenInitialize_thenInitOccurs_withProxy() {
        asyncService.initialize();
        await().untilCall(to(asyncService).isInitialized(), equalTo(true));
    }

    @Test
    public void givenAsyncService_whenInitialize_thenInitOccurs3() {
        asyncService.initialize();
        await().until(fieldIn(asyncService)
                .ofType(boolean.class)
                .andWithName("initialized"), equalTo(true));
    }

    @Test
    public void givenValue_whenAddValue_thenValueAdded() {
        asyncService.initialize();
        await().until(() -> asyncService.isInitialized());
        long value = 5;
        asyncService.addValue(value);
        await().until(asyncService::getValue, equalTo(value));
    }

    @Test
    public void givenAsyncService_whenGetValue_thenExceptionIgnored() {
        asyncService.initialize();
        given().ignoreException(IllegalStateException.class)
                .await()
                .atMost(Duration.FIVE_SECONDS)
                .atLeast(Duration.FIVE_HUNDRED_MILLISECONDS)
                .until(asyncService::getValue, equalTo(0L));
    }
}
