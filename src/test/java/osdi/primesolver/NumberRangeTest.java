package osdi.primesolver;

import org.junit.Assert;
import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class NumberRangeTest {

    @Test
    public void testSimpleValueRange() throws Exception {
        NumberRange range = new NumberRange(1L, 5L);
        Iterator<Long> list = range.iterator();

        assertTrue(list.hasNext());
        assertEquals(1L, list.next().longValue());
        assertTrue(list.hasNext());
        assertEquals(2L, list.next().longValue());
        assertTrue(list.hasNext());
        assertEquals(3L, list.next().longValue());
        assertTrue(list.hasNext());
        assertEquals(4L, list.next().longValue());
        assertTrue(list.hasNext());
        assertEquals(5L, list.next().longValue());
    }

    @Test
    public void testBackwardsRangeThrowsException() throws Exception {
        try {
            new NumberRange(10, 1);
            Assert.fail("expected exception of type IllegalStateException");
        } catch(IllegalStateException e) {
            assertEquals("start value < max value", e.getMessage());
        }
    }
}
