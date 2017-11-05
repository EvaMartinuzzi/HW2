package osdi.locks;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class MonitorTest {
    private volatile int value = 0;
    private final Monitor monitor = new Monitor();

    @Test
    public void testBasicCase() throws Exception {
        Thread t1 = new Thread(this::evenIncrement);
        Thread t2 = new Thread(this::oddIncrement);
        t1.start();
        t2.start();

        t1.join();
        t2.join();

        assertTrue(value > 1000);
    }

    private void evenIncrement() {
        do {
            monitor.sync((m)->{
                while(value % 2 == 1) {
                    m.Wait();
                }
                ++value;
                m.Pulse();
            });
        } while(value < 1000);
    }

    private void oddIncrement() {
        do {
            monitor.sync((m)->{
                while(value % 2 == 0) {
                    m.Wait();
                }
                ++value;
                m.Pulse();
            });
        } while(value < 1000);
    }
}
