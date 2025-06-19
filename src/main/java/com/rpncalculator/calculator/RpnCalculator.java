package com.rpncalculator.calculator;

import com.rpncalculator.exception.RpnCalculationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import java.util.Stack;

/**
 * A calculator that evaluates expressions in Reverse Polish Notation (RPN).
 */
public class RpnCalculator {

    private static final Logger logger = LoggerFactory.getLogger(RpnCalculator.class);

    /**
     * Evaluates a single RPN expression.
     *
     * @param expression The RPN expression as a space-separated string (e.g., "1.0 2.0 +").
     * @return The result of the calculation.
     * @throws RpnCalculationException if the expression is invalid, contains unknown operators,
     * or has insufficient/excess operands.
     */
    public double evaluate(String expression) throws RpnCalculationException {
        if (expression == null || expression.trim().isEmpty()) {
            throw new RpnCalculationException("Expression cannot be null or empty.");
        }

        ArrayDeque<Double> operands = new ArrayDeque<>();
        String[] tokens = expression.trim().split("\\s+"); // Split by one or more spaces

        logger.debug("Evaluating RPN expression: '{}'", expression);

        for (String token : tokens) {
            try {
                // First try to parse as a number
                double value = Double.parseDouble(token);
                operands.add(value);
                logger.debug("Pushed number: {}", value);
            } catch (NumberFormatException e) {
                // If not a number, it must be an operator
                ValidOperators operator;
                try {
                    operator = ValidOperators.fromSymbol(token);
                    logger.debug("Found operator: {}", operator.getSymbol());
                } catch (RpnCalculationException opEx) {
                    throw new RpnCalculationException("Invalid token or unknown operator: " + token, opEx);
                }

                if (operands.size() < operator.getOperandsRequired()) {
                    throw new RpnCalculationException(
                            "Insufficient operands for operator '" + operator.getSymbol() +
                                    "'. Expected " + operator.getOperandsRequired() +
                                    ", but found " + operands.size() + " on stack."
                    );
                }
                extracted(operator, operands);
            }
        }

        if (operands.size() != 1) {
            throw new RpnCalculationException(
                    "Invalid RPN expression: After calculation, " + operands.size() +
                            " values remain on the stack. Expected 1."
            );
        }
        //Pop the final result
        return operands.poll();
    }

    private static void extracted(ValidOperators operator, ArrayDeque<Double> operands) throws RpnCalculationException {
        // Pop operands based on the operator's requirement
        double[] args = new double[operator.getOperandsRequired()];
        for (int i = 0; i < operator.getOperandsRequired(); i++) {
            args[i] = operands.poll();
        }
        logger.debug("Popped operands for {}: {}", operator.getSymbol(), Arrays.toString(args));
        double result;
        try {
            result = operator.apply(args);
        } catch (RpnCalculationException calcEx) {
            throw new RpnCalculationException("Error applying operator '" + operator.getSymbol() + "': " + calcEx.getMessage(), calcEx);
        }

        operands.addFirst(result);
        if(operands.size() > 1){
            extracted(operator, operands);
        }
        logger.debug("Pushed result: {}", result);
    }
}
