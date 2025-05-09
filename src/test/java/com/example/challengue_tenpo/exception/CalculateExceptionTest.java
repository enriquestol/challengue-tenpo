package com.example.challengue_tenpo.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@DisplayName("Tests for CalculateException")
class CalculateExceptionTest {

    @Test
    @DisplayName("TestConstructor_OnlyMessage")
    void testConstructor_OnlyMessage() {
        // Arrange
        String message = "Calculation error";

        // Act
        CalculateException exception = new CalculateException(message);

        // Assert
        assertEquals(message, exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    @DisplayName("TestConstructor_MessageAndCause")
    void testConstructor_MessageAndCause() {
        // Arrange
        String message = "Calculation error";
        Throwable cause = new RuntimeException("Original cause");

        // Act
        CalculateException exception = new CalculateException(message, cause);

        // Assert
        assertEquals(message, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }
}