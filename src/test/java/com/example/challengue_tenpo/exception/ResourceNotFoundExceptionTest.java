package com.example.challengue_tenpo.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@DisplayName("Tests for ResourceNotFoundException")
class ResourceNotFoundExceptionTest {

    @Test
    @DisplayName("TestConstructor_OnlyMessage")
    void testConstructor_OnlyMessage() {
        // Arrange
        String message = "Resource not found";

        // Act
        ResourceNotFoundException exception = new ResourceNotFoundException(message);

        // Assert
        assertEquals(message, exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    @DisplayName("TestConstructor_MessageAndCause")
    void testConstructor_MessageAndCause() {
        // Arrange
        String message = "Resource not found";
        Throwable cause = new RuntimeException("Original cause");

        // Act
        ResourceNotFoundException exception = new ResourceNotFoundException(message, cause);

        // Assert
        assertEquals(message, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }
}