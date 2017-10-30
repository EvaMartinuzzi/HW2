package osdi.collections;

/*
 * you may not use anything in java.util.concurrent.* you may only use locks from osdi.locks.*
 */
public class BoundBuffer {
    public static <T> SimpleQueue<T> createBoundBufferWithSemaphores(int bufferSize) {
        return new BoundBufferImpl<>(bufferSize);
    }
}

