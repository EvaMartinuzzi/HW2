package osdi.collections;

import osdi.locks.*;

class BoundBufferImpl<T> implements SimpleQueue<T> {
    private final int size;
    private final java.util.Queue<T> queue;
    private Monitor monitor;
    private SpinLock lock;

    public BoundBufferImpl(int bufferSize) {
        size = bufferSize;
        queue = new java.util.ArrayDeque<>(bufferSize);
        monitor = new Monitor();
        lock = new SpinLock();
    }

    @Override
    public void enqueue(T item) {
        while (this.queue.size() == size) {
            monitor.sync((Monitor.MonitorOperations::Wait));
        }
        if (this.queue.size() >= 0) {
            monitor.sync((Monitor.MonitorOperations::Pulse));
        }
        lock.lock();
        queue.add(item);
        lock.unlock();
    }

    @Override
    public T dequeue() {
        while(queue.isEmpty()){
            monitor.sync((Monitor.MonitorOperations::Wait));
        }
        if(this.queue.size() <= size){
            monitor.sync((Monitor.MonitorOperations::Pulse));
        }
        lock.lock();
        T item = null;
        if (!queue.isEmpty()) {
            item = queue.remove();
        }
        lock.unlock();
        return item;
    }
}