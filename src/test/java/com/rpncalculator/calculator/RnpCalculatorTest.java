package com.rpncalculator.calculator;

import com.rpncalculator.exception.RpnCalculationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RpnCalculatorTest {

    private RpnCalculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new RpnCalculator();
    }

    @Test
    @DisplayName("Should correctly evaluate addition")
    void testAddition() throws RpnCalculationException {
        assertEquals(5.0, calculator.evaluate("2.0 3.0 +"));
    }

    @Test
    @DisplayName("Should correctly evaluate multiplication")
    void testMultiplication() throws RpnCalculationException {
        assertEquals(20.0, calculator.evaluate("4 5 *"));
    }

    @Test
    @DisplayName("Should correctly evaluate subtraction")
    void testSubtraction() throws RpnCalculationException {
        assertEquals(1.0, calculator.evaluate("10 9 -"));
    }

    @Test
    @DisplayName("Should correctly evaluate division")
    void testDivision() throws RpnCalculationException {
        assertEquals(3, calculator.evaluate("6 2 /"));
    }

    @Test
    @DisplayName("Should correctly evaluate complex expression with mixed operations")
    void testComplexExpression() throws RpnCalculationException {
        assertEquals(4.0, calculator.evaluate("6 3 * 2 - sqrt"));
    }

    @Test
    @DisplayName("Should correctly evaluate expression resulting in 0")
    void testExpressionToZero() throws RpnCalculationException {
        assertEquals(0.0, calculator.evaluate("4 2 - 2 - 1000 *"));
    }

    @Test
    @DisplayName("Should correctly evaluate square root")
    void testSqrt() throws RpnCalculationException {
        assertEquals(5.0, calculator.evaluate("25 sqrt"));
    }


    @Test
    @DisplayName("Should correctly evaluate sine (0 degrees)")
    void testSin() throws RpnCalculationException {
        assertEquals(0, calculator.evaluate("0 sin"));
    }

    @Test
    @DisplayName("Should correctly evaluate cosine (0 degrees)")
    void testCos() throws RpnCalculationException {
        assertEquals(1, calculator.evaluate("0 cos"));
    }

    @Test
    @DisplayName("Should correctly evaluate average of two numbers")
    void testAvg() throws RpnCalculationException {
        assertEquals(10, calculator.evaluate("8 12 avg"));
    }

    @Test
    @DisplayName("Should correctly evaluate modulus")
    void testMod() throws RpnCalculationException {
        assertEquals(1.0, calculator.evaluate("10 3 mod"));
    }

    @Test
    @DisplayName("Should throw exception for division by zero")
    void testDivideByZero() {
        RpnCalculationException thrown = assertThrows(RpnCalculationException.class, () -> {
            calculator.evaluate("10 0 /");
        });
        assertTrue(thrown.getMessage().contains("Arithmetic error during / operation: Division by zero"));
    }

    @Test
    @DisplayName("Should throw exception for modulus by zero")
    void testModByZero() {
        RpnCalculationException thrown = assertThrows(RpnCalculationException.class, () -> {
            calculator.evaluate("5 0 mod");
        });
        assertTrue(thrown.getMessage().contains("Arithmetic error during mod operation: Modulus by zero"));
    }

    @Test
    @DisplayName("Should throw exception for insufficient operands")
    void testInsufficientOperands() {
        RpnCalculationException thrown = assertThrows(RpnCalculationException.class, () -> {
            calculator.evaluate("2 +");
        });
        assertTrue(thrown.getMessage().contains("Insufficient operands for operator '+'"));
    }

    @Test
    @DisplayName("Should throw exception for unknown operator")
    void testUnknownOperator() {
        RpnCalculationException thrown = assertThrows(RpnCalculationException.class, () -> {
            calculator.evaluate("1 2 invalidOperator");
        });
        assertTrue(thrown.getMessage().contains("Invalid token or unknown operator: invalidOp"));
    }

    @Test
    @DisplayName("Should throw exception for malformed expression (too many operands)")
    void testMalformedExpressionTooManyOperands() {
        RpnCalculationException thrown = assertThrows(RpnCalculationException.class, () -> {
            calculator.evaluate("4 2 6 +");
        });
        assertTrue(thrown.getMessage().contains("Invalid RPN expression: After calculation, 2 values remain on the stack. Expected 1."));
    }

    @Test
    @DisplayName("Should throw exception for malformed expression (no operands left)")
    void testMalformedExpressionNoOperands() {
        RpnCalculationException thrown = assertThrows(RpnCalculationException.class, () -> {
            calculator.evaluate("+"); // No operands for +
        });
        assertTrue(thrown.getMessage().contains("Insufficient operands for operator '+'"));
    }

    @Test
    @DisplayName("Should throw exception for empty expression")
    void testEmptyExpression() {
        RpnCalculationException thrown = assertThrows(RpnCalculationException.class, () -> {
            calculator.evaluate("");
        });
        assertTrue(thrown.getMessage().contains("Expression cannot be null or empty."));
    }

}