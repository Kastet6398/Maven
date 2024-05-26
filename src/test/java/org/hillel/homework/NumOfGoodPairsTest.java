package org.hillel.homework;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class NumOfGoodPairsTest {
    @Test
    public void test1() {
        assertEquals(4, NumOfGoodPairs.numIdenticalPairs(new int[]{1, 2, 3, 1, 1, 3}));
    }

    @Test
    public void test2() {
        assertEquals(0, NumOfGoodPairs.numIdenticalPairs(new int[]{}));
    }

    @Test
    public void test3() {
        assertEquals(0, NumOfGoodPairs.numIdenticalPairs(new int[]{1, 2, 3}));
    }

    @Test
    public void test4() {
        assertEquals(0, NumOfGoodPairs.numIdenticalPairs(new int[]{1}));
    }

    @Test
    public void test5() {
        assertThrows(NullPointerException.class, () -> NumOfGoodPairs.numIdenticalPairs(null));
    }
}
