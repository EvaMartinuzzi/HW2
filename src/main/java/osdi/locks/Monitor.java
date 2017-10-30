package osdi.locks;

/*
 * Grad students only - re-write this in terms of osdi.locks.SpinLock
 */
/*
 * Grad students only - you may not use anything in java.util.concurrent.* you may only use locks from osdi.locks.*
 */
public class Monitor {

    public interface CodeToExecute {
        void run(MonitorOperations ops);
    }

    public interface MonitorOperations {
        void Wait();
        void pulse();
    }

    private final MonitorOperations lockObj = new MonitorOperationsImpl();

    public void sync(CodeToExecute fn) {
        synchronized (lockObj) {
            fn.run(lockObj);
        }
    }

    private static class MonitorOperationsImpl implements MonitorOperations {
        @Override
        public void Wait() {
            try {
                this.wait();
            } catch (InterruptedException e) {
            }
        }

        @Override
        public void pulse() {
            this.pulse();
        }
    }
}
