package org.hillel.homework;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class MaxNumberOfBalloonsTest {
    @Test
    public void maxNumberOfBalloons_validTextAndCanGetBalloon_correctCounterReturned() {
        assertEquals(2, MaxNumberOfBalloons.maxNumberOfBalloons("loonbalxballpoon"));
    }

    @Test
    public void maxNumberOfBalloons_emptyText_zeroReturned() {
        assertEquals(0, MaxNumberOfBalloons.maxNumberOfBalloons(""));
    }

    @Test
    public void maxNumberOfBalloons_validTextAndCannotGetBalloon_zeroReturned() {
        assertEquals(0, MaxNumberOfBalloons.maxNumberOfBalloons("nlaeboko"));
    }

    @Test
    public void maxNumberOfBalloons_textNull_NullPointerExcepionThrown() {
        assertThrows(NullPointerException.class, () -> MaxNumberOfBalloons.maxNumberOfBalloons(null));
    }
}
