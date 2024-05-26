package org.hillel.homework;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class NumOfGoodPairsTest {
    @Test
    public void numIdenticalPairs_validArrayWithGoodPairs_correctCounterReturned() {
        assertEquals(4, NumOfGoodPairs.numIdenticalPairs(new int[]{1, 2, 3, 1, 1, 3}));
    }

    @Test
    public void numIdenticalPairs_emptyArray_zeroReturned() {
        assertEquals(0, NumOfGoodPairs.numIdenticalPairs(new int[]{}));
    }

    @Test
    public void numIdenticalPairs_validArrayWithoutGoodPairs_zeroReturned() {
        assertEquals(0, NumOfGoodPairs.numIdenticalPairs(new int[]{1, 2, 3}));
    }

    @Test
    public void numIdenticalPairs_validArrayWithOneElement_zeroReturned() {
        assertEquals(0, NumOfGoodPairs.numIdenticalPairs(new int[]{1}));
    }

    @Test
    public void numIdenticalPairs_arrayNull_NullPointerExceptionThrown() {
        assertThrows(NullPointerException.class, () -> NumOfGoodPairs.numIdenticalPairs(null));
    }
}
