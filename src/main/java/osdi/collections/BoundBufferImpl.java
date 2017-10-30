package osdi.collections;

/*
 * Modify this as you see fit. you may not use anything in java.util.concurrent.* you may only use locks from osdi.locks.*
 */
class BoundBufferImpl<T> implements SimpleQueue<T> {
    private final int bufferSize;
    private final java.util.Queue<T> queue;

    public BoundBufferImpl(int bufferSize) {
        this.bufferSize = bufferSize;
        queue = new java.util.ArrayDeque<>();
    }

    @Override
    public void enqueue(T item) {
        queue.add(item);
    }

    @Override
    public T dequeue() {
        T item = queue.remove();
        return item;
    }
}
