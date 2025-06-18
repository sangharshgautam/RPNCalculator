package com.rpncalculator;

import com.rpncalculator.calculator.RpnCalculator;
import com.rpncalculator.exception.RpnCalculationException;
import com.rpncalculator.util.FileProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;

/**
 * Main application class for the RPN Calculator command-line tool.
 * Takes an input file path as a command-line argument, reads RPN expressions,
 * calculates them, and prints the results.
 */
public class Application {

    private static final Logger logger = LoggerFactory.getLogger(Application.class);
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.############");

    public static void main(String[] args) {
        logger.info("RPN Calculator Application Starting...");

        if (args.length != 1) {
            System.err.println("Usage: java -jar rpn-calculator.jar <input_file_path>");
            logger.error("Invalid number of command-line arguments. Expected 1 (input file path), got {}", args.length);
            System.exit(1);
        }

        String inputFilePath = args[0];
        FileProcessor fileProcessor = new FileProcessor();
        RpnCalculator calculator = new RpnCalculator();

        try {
            List<String> expressions = fileProcessor.readLines(inputFilePath);

            if (expressions.isEmpty()) {
                System.out.println("Input file is empty. No calculations to perform.");
                logger.warn("Input file '{}' is empty.", inputFilePath);
                return;
            }

            System.out.println("\n--- RPN Calculation Results ---");
            for (String expression : expressions) {
                if (expression.isEmpty()) {
                    System.out.println("Empty line skipped.");
                    continue;
                }
                try {
                    double result = calculator.evaluate(expression);
                    System.out.println(expression + " = " + DECIMAL_FORMAT.format(result));
                } catch (RpnCalculationException e) {
                    System.err.println(expression + " - Error: " + e.getMessage());
                    logger.error("Error calculating expression '{}': {}", expression, e.getMessage());
                } catch (Exception e) {
                    System.err.println(expression + " - Unexpected Error: " + e.getMessage());
                    logger.error("An unexpected error occurred for expression '{}': {}", expression, e.getMessage(), e);
                }
            }

        } catch (IOException e) {
            System.err.println("Error reading input file: " + e.getMessage());
            logger.error("Failed to read input file '{}': {}", inputFilePath, e.getMessage());
            System.exit(1);
        } catch (Exception e) {
            System.err.println("An unhandled application error occurred: " + e.getMessage());
            logger.error("An unhandled application error occurred: {}", e.getMessage(), e);
            System.exit(1);
        }

        logger.info("RPN Calculator Application Finished.");
    }
}