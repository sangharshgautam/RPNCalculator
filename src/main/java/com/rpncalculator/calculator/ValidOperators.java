package com.rpncalculator.calculator;

import com.rpncalculator.exception.RpnCalculationException;

import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleUnaryOperator;

/**
 * Enum representing supported RPN operators.
 * Each operator defines its symbol, number of operands required, and the operation logic.
 */
public enum ValidOperators {
    ADD("+", 2, (a, b) -> a + b),
    SUBTRACT("-", 2, (a, b) -> a - b),
    MULTIPLY("*", 2, (a, b) -> a * b),
    DIVIDE("/", 2, (a, b) -> {
        if (b == 0.0) {
            throw new ArithmeticException("Division by zero");
        }
        return a / b;
    }),
    SQRT("sqrt", 1, a -> Math.sqrt(a)),
    SIN("sin", 1, a -> Math.sin(Math.toRadians(a))),
    COS("cos", 1, a -> Math.cos(Math.toRadians(a))),
    AVG("avg", 2, (a, b) -> (a + b) / 2.0),
    MOD("mod", 2, (a, b) -> {
        if (b == 0.0) {
            throw new ArithmeticException("Modulus by zero");
        }
        return a % b;
    });

    private final String symbol;
    private final int operandsRequired;
    private final DoubleBinaryOperator binaryOperation;
    private final DoubleUnaryOperator unaryOperation;

    // Constructor for binary operators
    ValidOperators(String symbol, int operandsRequired, DoubleBinaryOperator operation) {
        this.symbol = symbol;
        this.operandsRequired = operandsRequired;
        this.binaryOperation = operation;
        this.unaryOperation = null; // Not applicable for binary operators
    }

    // Constructor for unary operators
    ValidOperators(String symbol, int operandsRequired, DoubleUnaryOperator operation) {
        this.symbol = symbol;
        this.operandsRequired = operandsRequired;
        this.unaryOperation = operation;
        this.binaryOperation = null; // Not applicable for unary operators
    }

    /**
     * Returns the operator enum for a given symbol.
     *
     * @param symbol The string symbol of the operator (e.g., "+", "sqrt").
     * @return The corresponding Operator enum.
     * @throws RpnCalculationException if the symbol does not match any known operator.
     */
    public static ValidOperators fromSymbol(String symbol) throws RpnCalculationException {
        for (ValidOperators op : values()) {
            if (op.symbol.equalsIgnoreCase(symbol)) {
                return op;
            }
        }
        throw new RpnCalculationException("Unknown operator: " + symbol);
    }

    /**
     * Performs the operation using the provided operands.
     *
     * @param operands An array of operands. The order matters for non-commutative operations.
     * @return The result of the operation.
     * @throws RpnCalculationException if the number of operands is incorrect for the operator,
     * or if an arithmetic error occurs (e.g., division by zero).
     */
    public double apply(double... operands) throws RpnCalculationException {
        if (operands.length != operandsRequired) {
            throw new RpnCalculationException(
                    "Operator '" + symbol + "' requires " + operandsRequired +
                            " operand(s) but received " + operands.length
            );
        }

        try {
            if (operandsRequired == 2 && binaryOperation != null) {
                // For binary operators, the operands are popped in reverse order,
                // so the first operand popped is 'b' and the second is 'a'
                // in the context of (a, b) -> a op b.
                // However, RPN calculation needs to follow the order of the original input.
                return binaryOperation.applyAsDouble(operands[0], operands[1]);
            } else if (operandsRequired == 1 && unaryOperation != null) {
                return unaryOperation.applyAsDouble(operands[0]);
            } else {
                throw new RpnCalculationException("Internal error: Operator type mismatch for " + symbol);
            }
        } catch (ArithmeticException e) {
            throw new RpnCalculationException("Arithmetic error during " + symbol + " operation: " + e.getMessage(), e);
        }
    }

    public int getOperandsRequired() {
        return operandsRequired;
    }

    public String getSymbol() {
        return symbol;
    }
}