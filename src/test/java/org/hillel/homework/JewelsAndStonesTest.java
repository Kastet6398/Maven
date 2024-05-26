package org.hillel.homework;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class JewelsAndStonesTest {
    @Test
    public void test1() {
        assertEquals(6, JewelsAndStones.numJewelsInStones("Ab", "aAAbbbb"));
    }

    @Test
    public void test2() {
        assertEquals(0, JewelsAndStones.numJewelsInStones("z", "ZZ"));
    }

    @Test
    public void test3() {
        assertThrows(NullPointerException.class, () -> JewelsAndStones.numJewelsInStones(null, "ZZ"));
    }

    @Test
    public void test4() {
        assertThrows(NullPointerException.class, () -> JewelsAndStones.numJewelsInStones("abc", null));
    }

    @Test
    public void test5() {
        assertThrows(NullPointerException.class, () -> JewelsAndStones.numJewelsInStones(null, null));
    }
}
