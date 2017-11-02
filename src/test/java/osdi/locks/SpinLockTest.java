package osdi.locks;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

public class SpinLockTest {

    private volatile int sharedValue = 0;
    private final SpinLock lock = new SpinLock();
    private static final int incrementTotal = 100000;
    private static final int threadCount = 20;

    @Test
    public void testLockAndUnlock() throws InterruptedException {
        sharedValue = 0;
        Collection<Thread> threads = new ArrayList<>();
        for(int i = 0; i < threadCount; i++) {
            threads.add(new Thread(this::adder));
        }
        for(Thread t : threads) {
            t.start();
        }
        for(Thread t : threads) {
            t.join();
        }

        assertEquals(threadCount*incrementTotal, sharedValue);
    }

    private void adder() {
        for(int i = 0; i < incrementTotal; i++) {
            try{
                lock.lock();
                ++sharedValue;
            } finally {
                lock.unlock();
            }
        }
    }
}
