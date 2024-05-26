package org.hillel.homework;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class JewelsAndStonesTest {
    @Test
    public void numJewelsInStones_validJewelsAndStonesAndStonesContainJewels_correctCounterReturned() {
        assertEquals(6, JewelsAndStones.numJewelsInStones("Ab", "aAAbbbb"));
    }

    @Test
    public void numJewelsInStones_validJewelsAndStonesAndStonesDoNotContainJewels_zeroReturned() {
        assertEquals(0, JewelsAndStones.numJewelsInStones("z", "ZZ"));
    }

    @Test
    public void numJewelsInStones_jewelsNullAndStonesValid_NullPointerExceptionThrown() {
        assertThrows(NullPointerException.class, () -> JewelsAndStones.numJewelsInStones(null, "ZZ"));
    }

    @Test
    public void numJewelsInStones_jewelsValidAndStonesNull_NullPointerExceptionThrown() {
        assertThrows(NullPointerException.class, () -> JewelsAndStones.numJewelsInStones("abc", null));
    }

    @Test
    public void numJewelsInStones_jewelsAndStonesNull_NullPointerExceptionThrown() {
        assertThrows(NullPointerException.class, () -> JewelsAndStones.numJewelsInStones(null, null));
    }
}
