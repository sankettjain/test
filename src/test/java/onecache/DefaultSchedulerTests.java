package onecache;

import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DefaultSchedulerTests {

    @Test
    void schedule() throws InterruptedException {

        Scheduler scheduler = new DefaultScheduler();
        final AtomicInteger timesCalled = new AtomicInteger();

        // Schedule some work
        scheduler.RunOnceAfter(() -> { timesCalled.getAndIncrement(); }, 15);
        Runnable cancel = scheduler.RunOnceAfter(() -> { timesCalled.getAndIncrement(); }, 30);
        scheduler.RunOnceAfter(() -> { timesCalled.getAndIncrement(); }, 35);
        assertEquals(0, timesCalled.get());

        // Sleep to ensure it has run
        Thread.sleep(20);
        assertEquals(1, timesCalled.get());

        // Cancel the second call
        cancel.run();

        // Sleep to allow the remaining (third) call to complete
        Thread.sleep(20);
        assertEquals(2, timesCalled.get());
    }
}
