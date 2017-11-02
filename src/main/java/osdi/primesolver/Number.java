package osdi.primesolver;

/*
 *  You may not modify this code.
 */
public class Number {
    public static boolean IsPrime(long value) {
        if(value <= 0) throw new IllegalStateException("value > 0");
        if(value <= 2) return true;
        if(value % 2 == 0) return false;
        long end = (long)Math.sqrt(value) + 1;
        for(long divisor = 3; divisor < end; divisor += 2) {
            if(value % divisor == 0) {
                return false;
            }
        }
        return true;
    }
}
