package seedu.address.logic.util;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * We do not need to test functions of ArrayDeque.
 * Instead, we need to verify that the cache updates properly.
 */
public class ArrayDequeWithRandomAccessTest {
    @Test
    public void add_singleEntry_success() {
        ArrayDequeWithRandomAccess<String> adl = new ArrayDequeWithRandomAccess<>();
        adl.addFirst("first");
        assertEquals("first", adl.get(0));
    }

    @Test
    public void add_manyEntries_getCorrectOrder() {
        ArrayDequeWithRandomAccess<String> adl = new ArrayDequeWithRandomAccess<>();
        adl.addFirst("first");
        adl.addFirst("second");
        adl.addFirst("third");
        assertEquals("third", adl.get(0));
        assertEquals("second", adl.get(1));
        assertEquals("first", adl.get(2));
    }

    @Test
    public void remove_last_entriesConsistent() {
        ArrayDequeWithRandomAccess<String> adl = new ArrayDequeWithRandomAccess<>();
        adl.addFirst("first");
        adl.addFirst("second");
        adl.addFirst("third");
        adl.removeLast();
        assertEquals(2, adl.size());
        assertEquals("third", adl.get(0));
        assertEquals("second", adl.get(1));
    }

    @Test
    public void checkEmpty_empty_isEmpty() {
        ArrayDequeWithRandomAccess<String> adl = new ArrayDequeWithRandomAccess<>();
        assertTrue(adl.isEmpty());
        assertThrows(IndexOutOfBoundsException.class, () -> adl.get(0));
    }

    @Test
    public void checkEmpty_notEmpty_isNotEmpty() {
        ArrayDequeWithRandomAccess<String> adl = new ArrayDequeWithRandomAccess<>();
        adl.addFirst("first");
        assertFalse(adl.isEmpty());
        assertDoesNotThrow(() -> adl.get(0));
    }

    @Test
    public void addMany_size_correct() {
        ArrayDequeWithRandomAccess<String> adl = new ArrayDequeWithRandomAccess<>();
        adl.addFirst("first");
        adl.addFirst("second");
        adl.addFirst("third");
        assertEquals(3, adl.size());
    }
}
