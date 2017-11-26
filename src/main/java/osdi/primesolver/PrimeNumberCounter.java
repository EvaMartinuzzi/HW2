package osdi.primesolver;

import osdi.collections.BoundBuffer;
import osdi.collections.SimpleQueue;

import java.util.ArrayList;
import java.util.Collection;

/*
 * you may not use anything in java.util.concurrent.* you may only use locks from osdi.locks.*
 */
public class PrimeNumberCounter {

    private long currentCount = 0L;

    /*
     * you may not modify this method
     */
    private static int getThreadCount() {
        return Runtime.getRuntime().availableProcessors()*4;
    }

    /*
     * you may not modify this method
     */
    private void startThreads(final SimpleQueue<Long> valuesToCheck, final SimpleQueue<Long> valuesThatArePrime) {
        Collection<Thread> threads = new ArrayList<>();
        int threadCount = getThreadCount();
        for(int i = 0; i < threadCount; i++) {
            Thread t =
                    new Thread(new Runnable() {

                        @Override
                        public void run() {
                            findPrimeValues(valuesToCheck, valuesThatArePrime);

                        }
                    });
            t.setDaemon(true);
            threads.add(t);
        }
        Thread counter = new Thread(new Runnable() {

            @Override
            public void run() {
                countPrimeValues(valuesThatArePrime);

            }
        });
        threads.add(counter);

        for(Thread t : threads) {
            t.setDaemon(true);
            t.start();
        }
    }

    /*
     * you may modify this method
     */
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

    /*
     * you may modify this method
     */
    private void findPrimeValues(SimpleQueue<Long> valuesToCheck, SimpleQueue<Long> valuesThatArePrime) {

        while(true) {

            Long current = valuesToCheck.dequeue();
            if (current!=null){
                if(current%1000000==0) {
                    System.out.println(current);
                }
                if(Number.IsPrime(current)) {
                    valuesThatArePrime.enqueue(current);
                }
            }



        }

    }

    /*
     * you may modify this method
     */
    private void countPrimeValues(SimpleQueue<Long> valuesThatArePrime) {
        while(true) {

            Long current = valuesThatArePrime.dequeue();
            if (current!=null){
                currentCount++;
            }

            if(currentCount!=0 && currentCount % 1000000 == 0) {
                System.out.println("There are at least " + currentCount + " prime values");
                System.out.flush();
            }
        }
    }
}