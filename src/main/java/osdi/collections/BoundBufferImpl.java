package osdi.collections;

import osdi.locks.*;

import java.util.ArrayDeque;

class BoundBufferImpl<T> implements SimpleQueue<T> {
    private final int bufferSize;
    private final java.util.Queue<T> queue;
    private Monitor monitor;
    private SpinLock lock;

    public BoundBufferImpl(int bufferSize) {
        this.bufferSize = bufferSize;
        queue = new ArrayDeque<>(bufferSize);
        monitor = new Monitor();
        lock = new SpinLock();
    }

    @Override
    public void enqueue(T item) {
        while (this.queue.size() == bufferSize) {
            monitor.sync((Monitor.MonitorOperations::Wait));
        }
        if (this.queue.size() >= 0) {
            monitor.sync((Monitor.MonitorOperations::Pulse));
        }
        lock.lock();
        if (item != null) {
            queue.add(item);
        }
        lock.unlock();

    }

    @Override
    public T dequeue() {
        while(queue.isEmpty()){
            monitor.sync((Monitor.MonitorOperations::Wait));
        }
        if(this.queue.size() <= bufferSize){
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