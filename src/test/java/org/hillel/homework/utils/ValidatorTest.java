package org.hillel.homework.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidatorTest {
    @Test
    void wholeNumberRangeTwoArguments_validArgumentsAndNumberInRange_trueReturned() {
        assertTrue(Validator.wholeNumberRange("1", "8", "8"));
    }

    @Test
    void wholeNumberRangeTwoArguments_validArgumentsAndNumberOutOfRange_falseReturned() {
        assertFalse(Validator.wholeNumberRange("7", "11", "12"));
    }

    @Test
    void wholeNumberRangeTwoArguments_invalidNumber_NumberFormatExceptionThrown() {
        assertThrows(NumberFormatException.class, () -> Validator.wholeNumberRange("7", "11", "a"));
    }

    @Test
    void wholeNumberRangeTwoArguments_nullNumber_NullPointerExceptionThrown() {
        assertThrows(NullPointerException.class, () -> Validator.wholeNumberRange("7", "11", null));
    }

    @Test
    void wholeNumberRangeTwoArguments_invalidStartAndValidEnd_NumberFormatExceptionThrown() {
        assertThrows(NumberFormatException.class, () -> Validator.wholeNumberRange("a", "11", "38"));
    }

    @Test
    void wholeNumberRangeTwoArguments_validStartAndInvalidEnd_NumberFormatExceptionThrown() {
        assertThrows(NumberFormatException.class, () -> Validator.wholeNumberRange("7", "a", "38"));
    }

    @Test
    void wholeNumberRangeTwoArguments_nullStartAndValidEnd_NullPointerExceptionThrown() {
        assertThrows(NullPointerException.class, () -> Validator.wholeNumberRange(null, "11", "38"));
    }

    @Test
    void wholeNumberRangeTwoArguments_validStartAndNullEnd_NullPointerExceptionThrown() {
        assertThrows(NullPointerException.class, () -> Validator.wholeNumberRange("7", null, "38"));
    }

    @Test
    void wholeNumberOneArgument_validArgumentAndNumberInRange_trueReturned() {
        assertTrue(Validator.wholeNumberRange("-8", "-6"));
    }

    @Test
    void wholeNumberRangeOneArgument_validArgumentAndNumberOutOfRange_falseReturned() {
        assertFalse(Validator.wholeNumberRange("9", "-11"));
    }

    @Test
    void wholeNumberRangeOneArgument_invalidNumber_NumberFormatExceptionThrown() {
        assertThrows(NumberFormatException.class, () -> Validator.wholeNumberRange("7", "a"));
    }

    @Test
    void wholeNumberRangeOneArgument_nullNumber_NullPointerExceptionThrown() {
        assertThrows(NullPointerException.class, () -> Validator.wholeNumberRange("7", null));
    }

    @Test
    void wholeNumberPointRangeOneArgument_invalidStart_NumberFormatExceptionThrown() {
        assertThrows(NumberFormatException.class, () -> Validator.wholeNumberRange("a", "38"));
    }

    @Test
    void wholeNumberRangeOneArgument_nullStart_NullPointerExceptionThrown() {
        assertThrows(NullPointerException.class, () -> Validator.wholeNumberRange(null, "38"));
    }
    @Test
    void floatingPointRangeTwoArguments_validArgumentsAndNumberInRange_trueReturned() {
        assertTrue(Validator.floatingPointRange("1.4", "8.9", "5.309"));
    }

    @Test
    void floatingPointRangeTwoArguments_validArgumentsAndNumberOutOfRange_falseReturned() {
        assertFalse(Validator.floatingPointRange("7", "11", "11.00000000001"));
    }

    @Test
    void floatingPointRangeTwoArguments_invalidNumber_NumberFormatExceptionThrown() {
        assertThrows(NumberFormatException.class, () -> Validator.floatingPointRange("7", "11", "a"));
    }

    @Test
    void floatingPointRangeTwoArguments_nullNumber_NullPointerExceptionThrown() {
        assertThrows(NullPointerException.class, () -> Validator.floatingPointRange("7", "11", null));
    }

    @Test
    void floatingPointRangeTwoArguments_invalidStartAndValidEnd_NumberFormatExceptionThrown() {
        assertThrows(NumberFormatException.class, () -> Validator.floatingPointRange("a", "11", "38"));
    }

    @Test
    void floatingPointRangeTwoArguments_validStartAndInvalidEnd_NumberFormatExceptionThrown() {
        assertThrows(NumberFormatException.class, () -> Validator.floatingPointRange("7", "a", "38"));
    }

    @Test
    void floatingPointRangeTwoArguments_nullStartAndValidEnd_NullPointerExceptionThrown() {
        assertThrows(NullPointerException.class, () -> Validator.floatingPointRange(null, "11", "38"));
    }

    @Test
    void floatingPointRangeTwoArguments_validStartAndNullEnd_NullPointerExceptionThrown() {
        assertThrows(NullPointerException.class, () -> Validator.floatingPointRange("7", null, "38"));
    }

    @Test
    void floatingPointRangeOneArgument_validArgumentAndNumberInRange_trueReturned() {
        assertTrue(Validator.floatingPointRange("-8.5", "5.309"));
    }

    @Test
    void floatingPointRangeOneArgument_validArgumentAndNumberOutOfRange_falseReturned() {
        assertFalse(Validator.floatingPointRange("9", "-11"));
    }

    @Test
    void floatingPointRangeOneArgument_invalidNumber_NumberFormatExceptionThrown() {
        assertThrows(NumberFormatException.class, () -> Validator.floatingPointRange("7", "a"));
    }

    @Test
    void floatingPointRangeOneArgument_nullNumber_NullPointerExceptionThrown() {
        assertThrows(NullPointerException.class, () -> Validator.floatingPointRange("7", null));
    }

    @Test
    void floatingPointRangeOneArgument_invalidStart_NumberFormatExceptionThrown() {
        assertThrows(NumberFormatException.class, () -> Validator.floatingPointRange("a", "38"));
    }

    @Test
    void floatingPointRangeOneArgument_nullStart_NullPointerExceptionThrown() {
        assertThrows(NullPointerException.class, () -> Validator.floatingPointRange(null, "38"));
    }

    @Test
    void stringNotEmpty_notEmpty_trueReturned() {
        assertTrue(Validator.stringNotEmpty("something"));
    }

    @Test
    void stringNotEmpty_empty_falseReturned() {
        assertFalse(Validator.stringNotEmpty(""));
    }

    @Test
    void stringNotEmpty_null_NullPointerExceptionThrown() {
        assertThrows(NullPointerException.class, () -> Validator.stringNotEmpty(null));
    }

    @Test
    void stringNotSpaces_notSpaces_trueReturned() {
        assertTrue(Validator.stringNotSpaces("something"));
    }

    @Test
    void stringNotSpaces_spaces_falseReturned() {
        assertFalse(Validator.stringNotSpaces("    "));
    }

    @Test
    void stringNotSpaces_null_NullPointerExceptionThrown() {
        assertThrows(NullPointerException.class, () -> Validator.stringNotSpaces(null));
    }

    @Test
    void isWholeNumber_valid_trueReturned() {
        assertTrue(Validator.isWholeNumber("123"));
    }

    @Test
    void isWholeNumber_invalid_falseReturned() {
        assertFalse(Validator.isWholeNumber("g"));
    }

    @Test
    void isWholeNumber_null_NullPointerExceptionThrown() {
        assertThrows(NullPointerException.class, () -> Validator.isWholeNumber(null));
    }

    @Test
    void isFloatingPointNumber_valid_trueReturned() {
        assertTrue(Validator.isFloatingPointNumber("-123.456"));
    }

    @Test
    void isFloatingPointNumber_invalid_falseReturned() {
        assertFalse(Validator.isFloatingPointNumber("98."));
    }

    @Test
    void isFloatingPointNumber_null_NullPointerExceptionThrown() {
        assertThrows(NullPointerException.class, () -> Validator.isFloatingPointNumber(null));
    }
}
