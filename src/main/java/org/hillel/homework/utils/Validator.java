package org.hillel.homework.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Validator<T> {
    public static boolean floatingPointRange(String start, String end, String input) {

        if (input == null || start == null || end == null) {
            throw new NullPointerException();
        }

        if (!(isFloatingPointNumber(input) && isFloatingPointNumber(start) && isFloatingPointNumber(end))) {
            throw new NumberFormatException();
        }

        String[] startFP = start.split("\\.");
        String[] endFP = end.split("\\.");
        String[] inputFP = input.split("\\.");

        if ((startFP[0].charAt(0) != '-' && inputFP[0].charAt(0) == '-') || (endFP[0].charAt(0) == '-' && inputFP[0].charAt(0) != '-')) {
            return false;
        }

        if (startFP[0].charAt(0) == '-') {
            startFP[0] = startFP[0].substring(1, startFP[0].length() - 1);
        }
        if (endFP[0].charAt(0) == '-') {
            endFP[0] = endFP[0].substring(1, endFP[0].length() - 1);
        }
        if (inputFP[0].charAt(0) == '-') {
            inputFP[0] = inputFP[0].substring(1, inputFP[0].length() - 1);
        }

        if (startFP[0].length() > inputFP[0].length() || endFP[0].length() < inputFP[0].length()) {
            return false;
        }

        String startString = startFP[0];
        if (startFP.length == 2) {
            startString += startFP[1];
        }
        String endString = endFP[0];
        if (endFP.length == 2) {
            endString += endFP[1];
        }
        input = inputFP[0];
        if (inputFP.length == 2) {
            input += inputFP[1];
        }

        for (int i = 0; i < Math.max(input.length(), Math.max(startString.length(), endString.length())); i++) {
            char startStringChar = '0';
            if (i < startString.length()) {
                startStringChar = startString.charAt(i);
            }
            char endStringChar = '0';
            if (i < endString.length()) {
                endStringChar = endString.charAt(i);
            }
            char inputStringChar = input.charAt(i);

            if (inputStringChar < startStringChar) {
                return false;
            } else if (inputStringChar > endStringChar) {
                return false;
            } else if (inputStringChar > startStringChar) {
                return true;
            } else if (inputStringChar < endStringChar) {
                return true;
            }
        }

        return true;
    }

    public static boolean floatingPointRange(String start, String input) {
        if (input == null || start == null) {
            throw new NullPointerException();
        }

        if (!(isFloatingPointNumber(input) && isFloatingPointNumber(start))) {
            throw new NumberFormatException();
        }

        String[] startFP = start.split("\\.");
        String[] inputFP = input.split("\\.");

        if (startFP[0].charAt(0) != '-' && inputFP[0].charAt(0) == '-') {
            return false;
        }

        if (startFP[0].charAt(0) == '-') {
            startFP[0] = startFP[0].substring(1, startFP[0].length() - 1);
        }
        if (inputFP[0].charAt(0) == '-') {
            inputFP[0] = inputFP[0].substring(1, inputFP[0].length() - 1);
        }

        if (startFP[0].length() > inputFP[0].length()) {
            return false;
        }

        String startString = startFP[0];
        if (startFP.length == 2) {
            startString += startFP[1];
        }
        String inputString = inputFP[0];
        if (inputFP.length == 2) {
            inputString += inputFP[1];
        }

        for (int i = 0; i < Math.max(inputString.length(), startString.length()); i++) {
            char startStringChar = '0';
            if (i < startString.length()) {
                startStringChar = startString.charAt(i);
            }
            char inputStringChar = '0';
            if (i < inputString.length()) {
                inputStringChar = inputString.charAt(i);
            }
            if (inputStringChar < startStringChar) {
                return false;
            } else if (inputStringChar > startStringChar) {
                return true;
            }
        }

        return true;

    }


    public static boolean stringNotSpaces(String input) {
        if (input == null) {
            throw new NullPointerException();
        }
        return !input.matches("\\s+");
    }

    public static boolean stringNotEmpty(String input) {
        if (input == null) {
            throw new NullPointerException();
        }
        return !input.isEmpty();
    }

    public static boolean wholeNumberRange(String start, String input) {
        if (input == null || start == null) {
            throw new NullPointerException();
        }

        if (!(isWholeNumber(input) && isWholeNumber(start))) {
            throw new NumberFormatException();
        }
        String startString = start;

        if (startString.charAt(0) != '-' && input.charAt(0) == '-') {
            return false;
        }

        if (startString.charAt(0) == '-') {
            startString = startString.substring(1, startString.length() - 1);
        }
        if (input.charAt(0) == '-') {
            input = input.substring(1, input.length() - 1);
        }

        if (startString.length() > input.length()) {
            return false;
        }

        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) < startString.charAt(i)) {
                return false;
            } else if (input.charAt(i) > startString.charAt(i)) {
                return true;
            }
        }

        return true;
    }

    public static boolean wholeNumberRange(String start, String end, String input) {
        if (input == null || start == null || end == null) {
            throw new NullPointerException();
        }

        if (!(isWholeNumber(input) && isWholeNumber(start) && isWholeNumber(end))) {
            throw new NumberFormatException();
        }

        String startString = start;
        String endString = end;

        if ((startString.charAt(0) != '-' && input.charAt(0) == '-') || (endString.charAt(0) == '-' && input.charAt(0) != '-')) {
            return false;
        }

        if (startString.charAt(0) == '-') {
            startString = startString.substring(1, startString.length() - 1);
        }
        if (endString.charAt(0) == '-') {
            endString = endString.substring(1, endString.length() - 1);
        }
        if (input.charAt(0) == '-') {
            input = input.substring(1, input.length() - 1);
        }

        if (startString.length() > input.length() || endString.length() < input.length()) {
            return false;
        }

        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) < startString.charAt(i)) {
                return false;
            } else if (input.charAt(i) > endString.charAt(i)) {
                return false;
            } else if (input.charAt(i) < endString.charAt(i)) {
                return true;
            } else if (input.charAt(i) > startString.charAt(i)) {
                return true;
            }
        }

        return true;
    }

    public static boolean isWholeNumber(String input) {
        if (input == null) {
            throw new NullPointerException();
        }
        int length = input.length();
        if (length == 0) {
            return false;
        }
        int i = 0;
        if (input.charAt(0) == '-') {
            if (length == 1) {
                return false;
            }
            i = 1;
        }
        for (; i < length; i++) {
            char c = input.charAt(i);
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }

    public static boolean isFloatingPointNumber(String input) {
        if (input == null) {
            throw new NullPointerException();
        }
        int length = input.length();
        if (length == 0) {
            return false;
        }
        int i = 0;

        if (input.charAt(0) == '.') {
            return false;
        }

        if (input.charAt(0) == '-') {
            if (length == 1) {
                return false;
            }
            i = 1;
        }
        boolean containsPeriod = false;
        for (; i < length; i++) {
            char c = input.charAt(i);
            if (c == '.') {
                if (containsPeriod || i == length - 1) {
                    return false;
                }
                containsPeriod = true;
            } else if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }
}
