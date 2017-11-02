package osdi.locks;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SemaphoreTest {

    private Semaphore sema;
    private int value;

    @Test
    public void testSemaphoreEventKind() throws InterruptedException {
        sema = new Semaphore(0);
        value = 0;
        Thread t = new Thread(this::eventWaiter);
        t.setDaemon(true);
        t.start();
        Thread.sleep(250);
        assertEquals(0, value);

        sema.up();

        Thread.sleep(250);

        assertEquals(1, value);
    }

    @Test
    public void testCountingSemaphore() throws InterruptedException {
        sema = new Semaphore(0);
        value = 0;
        Thread t = new Thread(this::adder);
        t.start();

        Thread.sleep(250);
        assertEquals(0, value);

        sema.up();
        Thread.sleep(250);
        assertEquals(1, value);

        sema.up();
        Thread.sleep(250);
        assertEquals(2, value);

        sema.up();
        Thread.sleep(250);
        assertEquals(3, value);

        sema.up();
        Thread.sleep(250);
        assertEquals(4, value);

        sema.up();
        Thread.sleep(250);
        assertEquals(5, value);

        sema.up();
        Thread.sleep(250);
        assertEquals(6, value);

        sema.up();
        Thread.sleep(250);
        assertEquals(7, value);

        sema.up();
        Thread.sleep(250);
        assertEquals(8, value);

        sema.up();
        Thread.sleep(250);
        assertEquals(9, value);

        sema.up();
        Thread.sleep(250);
        assertEquals(10, value);

        t.join(5000);

        assertEquals(10, value);
    }

    private void eventWaiter() {
        sema.down();
        ++value;
    }

    private void adder() {
        while(value < 10) {
            sema.down();
            ++value;
        }
    }
}
