package com.rpncalculator.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for reading lines from a text file.
 */
public class FileProcessor {

    private static final Logger logger = LoggerFactory.getLogger(FileProcessor.class);

    /**
     * Reads all lines from the specified file.
     *
     * @param filePath The path to the input file.
     * @return A list of strings, where each string is a line from the file.
     * @throws IOException if an I/O error occurs reading the file.
     */
    public List<String> readLines(String filePath) throws IOException {
        List<String> lines = new ArrayList<>();
        logger.info("Attempting to read file: {}", filePath);
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
                logger.debug("Read line: {}", line);
            }
            logger.info("Successfully read {} lines from file: {}", lines.size(), filePath);
        } catch (IOException e) {
            logger.error("Error reading file {}: {}", filePath, e.getMessage());
            throw e;
        }
        return lines;
    }
}