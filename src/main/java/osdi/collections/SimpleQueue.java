package osdi.collections;

/*
 * change this if you feel it is necessary
 */
/*
 * you may not use anything in java.util.concurrent.* you may only use locks from osdi.locks.*
 */
public interface SimpleQueue<T> {
    void enqueue(T item);
    T dequeue();
}
