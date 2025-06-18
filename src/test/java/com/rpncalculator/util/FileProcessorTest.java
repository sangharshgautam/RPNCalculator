package com.rpncalculator.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileProcessorTest {

    @TempDir
    Path tempDir; //  Temporary directory for tests

    @Test
    @DisplayName("Should read all lines from a valid file")
    void testReadLines_ValidFile() throws IOException {
        Path tempFile = tempDir.resolve("test_input.txt");
        String content = "line1\nline2\nline3";
        Files.writeString(tempFile, content);

        FileProcessor fileProcessor = new FileProcessor();
        List<String> lines = fileProcessor.readLines(tempFile.toString());

        assertNotNull(lines);
        assertEquals(3, lines.size());
        assertEquals("line1", lines.get(0));
        assertEquals("line2", lines.get(1));
        assertEquals("line3", lines.get(2));
    }

    @Test
    @DisplayName("Should return empty list for an empty file")
    void testReadLines_EmptyFile() throws IOException {
        Path tempFile = tempDir.resolve("empty_file.txt");
        Files.writeString(tempFile, ""); // Create an empty file

        FileProcessor fileProcessor = new FileProcessor();
        List<String> lines = fileProcessor.readLines(tempFile.toString());

        assertNotNull(lines);
        assertTrue(lines.isEmpty());
    }

    @Test
    @DisplayName("Should throw IOException for a non-existent file")
    void testReadLines_NonExistentFile() {
        FileProcessor fileProcessor = new FileProcessor();
        String nonExistentPath = tempDir.resolve("non_existent_file.txt").toString();

        IOException thrown = assertThrows(IOException.class, () -> {
            fileProcessor.readLines(nonExistentPath);
        });
        assertTrue(thrown.getMessage().contains("No such file or directory"));
    }
}