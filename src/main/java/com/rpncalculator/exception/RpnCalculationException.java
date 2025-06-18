package com.rpncalculator.exception;

/**
 * Custom exception for errors encountered during RPN calculation.
 */
public class RpnCalculationException extends Exception {

    public RpnCalculationException(String message) {
        super(message);
    }

    public RpnCalculationException(String message, Throwable cause) {
        super(message, cause);
    }
}