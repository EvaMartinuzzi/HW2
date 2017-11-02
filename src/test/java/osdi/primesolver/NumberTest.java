package osdi.primesolver;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class NumberTest {

    @Test
    public void testSimpleCases() {

        assertTrue(Number.IsPrime(1L));
        assertTrue(Number.IsPrime(2L));
        assertTrue(Number.IsPrime(3L));
        assertTrue(Number.IsPrime(5L));
        assertTrue(Number.IsPrime(7L));
        assertTrue(Number.IsPrime(11L));
        assertTrue(Number.IsPrime(13L));

        assertFalse(Number.IsPrime(4L));
        assertFalse(Number.IsPrime(6L));
        assertFalse(Number.IsPrime(8L));
        assertFalse(Number.IsPrime(9L));
        assertFalse(Number.IsPrime(12L));
    }
}
