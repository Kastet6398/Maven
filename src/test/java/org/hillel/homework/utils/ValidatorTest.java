package org.hillel.homework.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ValidatorTest {
    @Test
    public void wholeNumberRangeTwoArguments_validArgumentsAndNumberInRange_trueReturned() {
        assertTrue(Validator.wholeNumberRange("1", "8", "8"));
    }

    @Test
    public void wholeNumberRangeTwoArguments_validArgumentsAndNumberOutOfRange_falseReturned() {
        assertFalse(Validator.wholeNumberRange("7", "11", "12"));
    }

    @Test
    public void wholeNumberRangeTwoArguments_invalidNumber_NumberFormatExceptionThrown() {
        assertThrows(NumberFormatException.class, () -> Validator.wholeNumberRange("7", "11", "a"));
    }

    @Test
    public void wholeNumberRangeTwoArguments_nullNumber_NullPointerExceptionThrown() {
        assertThrows(NullPointerException.class, () -> Validator.wholeNumberRange("7", "11", null));
    }

    @Test
    public void wholeNumberRangeTwoArguments_invalidStartAndValidEnd_NumberFormatExceptionThrown() {
        assertThrows(NumberFormatException.class, () -> Validator.wholeNumberRange("a", "11", "38"));
    }

    @Test
    public void wholeNumberRangeTwoArguments_validStartAndInvalidEnd_NumberFormatExceptionThrown() {
        assertThrows(NumberFormatException.class, () -> Validator.wholeNumberRange("7", "a", "38"));
    }

    @Test
    public void wholeNumberRangeTwoArguments_nullStartAndValidEnd_NullPointerExceptionThrown() {
        assertThrows(NullPointerException.class, () -> Validator.wholeNumberRange(null, "11", "38"));
    }

    @Test
    public void wholeNumberRangeTwoArguments_validStartAndNullEnd_NullPointerExceptionThrown() {
        assertThrows(NullPointerException.class, () -> Validator.wholeNumberRange("7", null, "38"));
    }

    @Test
    public void wholeNumberOneArgument_validArgumentAndNumberInRange_trueReturned() {
        assertTrue(Validator.wholeNumberRange("-8", "-6"));
    }

    @Test
    public void wholeNumberRangeOneArgument_validArgumentAndNumberOutOfRange_falseReturned() {
        assertFalse(Validator.wholeNumberRange("9", "-11"));
    }

    @Test
    public void wholeNumberRangeOneArgument_invalidNumber_NumberFormatExceptionThrown() {
        assertThrows(NumberFormatException.class, () -> Validator.wholeNumberRange("7", "a"));
    }

    @Test
    public void wholeNumberRangeOneArgument_nullNumber_NullPointerExceptionThrown() {
        assertThrows(NullPointerException.class, () -> Validator.wholeNumberRange("7", null));
    }

    @Test
    public void wholeNumberPointRangeOneArgument_invalidStart_NumberFormatExceptionThrown() {
        assertThrows(NumberFormatException.class, () -> Validator.wholeNumberRange("a", "38"));
    }

    @Test
    public void wholeNumberRangeOneArgument_nullStart_NullPointerExceptionThrown() {
        assertThrows(NullPointerException.class, () -> Validator.wholeNumberRange(null, "38"));
    }
    @Test
    public void floatingPointRangeTwoArguments_validArgumentsAndNumberInRange_trueReturned() {
        assertTrue(Validator.floatingPointRange("1.4", "8.9", "5.309"));
    }

    @Test
    public void floatingPointRangeTwoArguments_validArgumentsAndNumberOutOfRange_falseReturned() {
        assertFalse(Validator.floatingPointRange("7", "11", "11.00000000001"));
    }

    @Test
    public void floatingPointRangeTwoArguments_invalidNumber_NumberFormatExceptionThrown() {
        assertThrows(NumberFormatException.class, () -> Validator.floatingPointRange("7", "11", "a"));
    }

    @Test
    public void floatingPointRangeTwoArguments_nullNumber_NullPointerExceptionThrown() {
        assertThrows(NullPointerException.class, () -> Validator.floatingPointRange("7", "11", null));
    }

    @Test
    public void floatingPointRangeTwoArguments_invalidStartAndValidEnd_NumberFormatExceptionThrown() {
        assertThrows(NumberFormatException.class, () -> Validator.floatingPointRange("a", "11", "38"));
    }

    @Test
    public void floatingPointRangeTwoArguments_validStartAndInvalidEnd_NumberFormatExceptionThrown() {
        assertThrows(NumberFormatException.class, () -> Validator.floatingPointRange("7", "a", "38"));
    }

    @Test
    public void floatingPointRangeTwoArguments_nullStartAndValidEnd_NullPointerExceptionThrown() {
        assertThrows(NullPointerException.class, () -> Validator.floatingPointRange(null, "11", "38"));
    }

    @Test
    public void floatingPointRangeTwoArguments_validStartAndNullEnd_NullPointerExceptionThrown() {
        assertThrows(NullPointerException.class, () -> Validator.floatingPointRange("7", null, "38"));
    }

    @Test
    public void floatingPointRangeOneArgument_validArgumentAndNumberInRange_trueReturned() {
        assertTrue(Validator.floatingPointRange("-8.5", "5.309"));
    }

    @Test
    public void floatingPointRangeOneArgument_validArgumentAndNumberOutOfRange_falseReturned() {
        assertFalse(Validator.floatingPointRange("9", "-11"));
    }

    @Test
    public void floatingPointRangeOneArgument_invalidNumber_NumberFormatExceptionThrown() {
        assertThrows(NumberFormatException.class, () -> Validator.floatingPointRange("7", "a"));
    }

    @Test
    public void floatingPointRangeOneArgument_nullNumber_NullPointerExceptionThrown() {
        assertThrows(NullPointerException.class, () -> Validator.floatingPointRange("7", null));
    }

    @Test
    public void floatingPointRangeOneArgument_invalidStart_NumberFormatExceptionThrown() {
        assertThrows(NumberFormatException.class, () -> Validator.floatingPointRange("a", "38"));
    }

    @Test
    public void floatingPointRangeOneArgument_nullStart_NullPointerExceptionThrown() {
        assertThrows(NullPointerException.class, () -> Validator.floatingPointRange(null, "38"));
    }

    @Test
    public void stringNotEmpty_notEmpty_trueReturned() {
        assertTrue(Validator.stringNotEmpty("something"));
    }

    @Test
    public void stringNotEmpty_empty_falseReturned() {
        assertFalse(Validator.stringNotEmpty(""));
    }

    @Test
    public void stringNotEmpty_null_NullPointerExceptionThrown() {
        assertThrows(NullPointerException.class, () -> Validator.stringNotEmpty(null));
    }

    @Test
    public void stringNotSpaces_notSpaces_trueReturned() {
        assertTrue(Validator.stringNotSpaces("something"));
    }

    @Test
    public void stringNotSpaces_spaces_falseReturned() {
        assertFalse(Validator.stringNotSpaces("    "));
    }

    @Test
    public void stringNotSpaces_null_NullPointerExceptionThrown() {
        assertThrows(NullPointerException.class, () -> Validator.stringNotSpaces(null));
    }

    @Test
    public void isWholeNumber_valid_trueReturned() {
        assertTrue(Validator.isWholeNumber("123"));
    }

    @Test
    public void isWholeNumber_invalid_falseReturned() {
        assertFalse(Validator.isWholeNumber("g"));
    }

    @Test
    public void isWholeNumber_null_NullPointerExceptionThrown() {
        assertThrows(NullPointerException.class, () -> Validator.isWholeNumber(null));
    }

    @Test
    public void isFloatingPointNumber_valid_trueReturned() {
        assertTrue(Validator.isFloatingPointNumber("-123.456"));
    }

    @Test
    public void isFloatingPointNumber_invalid_falseReturned() {
        assertFalse(Validator.isFloatingPointNumber("98."));
    }

    @Test
    public void isFloatingPointNumber_null_NullPointerExceptionThrown() {
        assertThrows(NullPointerException.class, () -> Validator.isFloatingPointNumber(null));
    }
}
