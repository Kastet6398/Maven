package com.hillel.homework.utils;

import com.hillel.homework.utils.exceptions.CalculationException;
import org.mariuszgromada.math.mxparser.*;

public final class Calculator {
    static {
        License.iConfirmNonCommercialUse("Kastet6398");
    }

    private Calculator() {
        throw new RuntimeException("Utility class Calculator is not instantiated");
    }

    public static double calculate(String expression) throws CalculationException {
        Expression expressionInstance = new Expression(expression);
        if (!(expressionInstance.checkSyntax() && expressionInstance.checkLexSyntax())) {
            throw new CalculationException(expressionInstance.getErrorMessage());
        }
        return expressionInstance.calculate();
    }
}
