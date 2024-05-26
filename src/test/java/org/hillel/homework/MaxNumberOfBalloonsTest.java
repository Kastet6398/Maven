package org.hillel.homework;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class MaxNumberOfBalloonsTest {
    @Test
    public void test1() {
        assertEquals(2, MaxNumberOfBalloons.maxNumberOfBalloons("loonbalxballpoon"));
    }

    @Test
    public void test2() {
        assertEquals(0, MaxNumberOfBalloons.maxNumberOfBalloons(""));
    }

    @Test
    public void test3() {
        assertEquals(0, MaxNumberOfBalloons.maxNumberOfBalloons("nlaeboko"));
    }

    @Test
    public void test4() {
        assertThrows(NullPointerException.class, () -> MaxNumberOfBalloons.maxNumberOfBalloons(null));
    }
}