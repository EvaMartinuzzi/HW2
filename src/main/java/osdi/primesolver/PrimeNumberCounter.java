package osdi.primesolver;

import osdi.collections.BoundBuffer;
import osdi.collections.SimpleQueue;

import java.util.ArrayList;
import java.util.Collection;

public class PrimeNumberCounter {

    private long currentCount = 0L;

    private static int getThreadCount() {
        return Runtime.getRuntime().availableProcessors() * 4;
    }

    private void startThreads(SimpleQueue<Long> valuesToCheck, SimpleQueue<Long> valuesThatArePrime) {
        Collection<Thread> threads = new ArrayList<>();
        int threadCount = getThreadCount();
        for(int i = 0; i < threadCount; i++) {
            Thread t = new Thread(()->findPrimeValues(valuesToCheck, valuesThatArePrime));
            t.setDaemon(true);
            threads.add(t);
        }
        Thread counter = new Thread(()->countPrimeValues(valuesThatArePrime));
        threads.add(counter);

        for(Thread t : threads) {
            t.setDaemon(true);
            t.start();
        }
    }

    public long countPrimeNumbers(NumberRange range) {
        SimpleQueue<Long> valuesToCheck = BoundBuffer.createBoundBufferWithSemaphores(10000);
        SimpleQueue<Long> valuesThatArePrime = BoundBuffer.createBoundBufferWithSemaphores(10000);

        startThreads(valuesToCheck, valuesThatArePrime);
        for(Long value : range) {
            valuesToCheck.enqueue(value);
        }
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return currentCount;
    }

    private void findPrimeValues(SimpleQueue<Long> valuesToCheck, SimpleQueue<Long> valuesThatArePrime) {
        while(true) {
            Long current = valuesToCheck.dequeue();
            if (current!=null && Number.IsPrime(current)) {
                    valuesThatArePrime.enqueue(current);
            }
        }
    }

    private void countPrimeValues(SimpleQueue<Long> valuesThatArePrime) {
        while(true) {
            valuesThatArePrime.dequeue();
            currentCount++;
            if(currentCount!=0 && currentCount % 10000 == 0) {
                System.out.println("There are at least " + currentCount + " prime values");
                System.out.flush();
            }
        }
    }
}