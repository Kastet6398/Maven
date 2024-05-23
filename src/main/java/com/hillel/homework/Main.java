package com.hillel.homework;

import com.hillel.homework.utils.Calculator;
import com.hillel.homework.utils.exceptions.CalculationException;

public class Main {
    public static void main(String[] args) {
        try {
            System.out.println(Calculator.calculate(String.join(" ", args)));
        } catch (CalculationException e) {
            System.out.println(STR."ERROR: \{e.getMessage()}");
        }
    }
}
