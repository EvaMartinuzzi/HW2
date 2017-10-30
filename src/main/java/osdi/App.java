package osdi;

import osdi.primesolver.NumberRange;
import osdi.primesolver.PrimeNumberCounter;

/*
 * you may not use anything in java.util.concurrent.* you may only use locks from osdi.locks.*
 */
public class App 
{
    //you may not change the value of startValue or endValue
    private static final long startValue = 1000L;
    private static final long thousand = 1000L;
    private static final long billion = thousand * thousand * thousand;
    private static final long endValue = 5L * billion;

    /*
     * you may change this method. you must use NumberRange, PrimeNumberCounter, and reportNumberOfPrimes in here
     */
    public static void main( String[] args )
    {
        NumberRange range = new NumberRange(startValue, endValue);
        long numberOfPrimesInRange = new PrimeNumberCounter().countPrimeNumbers(range);

        reportNumberOfPrimes(numberOfPrimesInRange);
    }

    /*
     * you may not change this method
     */
    private static void reportNumberOfPrimes(long count) {
        System.out.println("There are " + count + " primes in the range");
        System.out.flush();
    }
}
