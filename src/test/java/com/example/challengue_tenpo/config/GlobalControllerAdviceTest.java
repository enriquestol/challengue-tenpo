package com.example.challengue_tenpo.config;

import com.example.challengue_tenpo.dto.ErrorResponse;
import com.example.challengue_tenpo.exception.ResourceNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;



import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Tests para GlobalControllerAdvice")
class GlobalControllerAdviceTest {

    private final GlobalControllerAdvice advice = new GlobalControllerAdvice();


    @Test
    @DisplayName("TestHandleIllegalArgumentException_WhenThrown_ReturnsBadRequest")
    void testHandleIllegalArgumentException() {
        // Arrange
        IllegalArgumentException exception = new IllegalArgumentException("Invalid argument");

        // Act
        ResponseEntity<ErrorResponse> response = advice.handleIllegalArgumentException(exception);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid argument", response.getBody().getMessage());
    }

    @Test
    @DisplayName("TestHandleResourceNotFoundException_WhenThrown_ReturnsNotFound")
    void testHandleResourceNotFoundException() {
        // Arrange
        ResourceNotFoundException exception = new ResourceNotFoundException("Resource not found");

        // Act
        ResponseEntity<ErrorResponse> response = advice.handleResourceNotFoundException(exception);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Resource not found", response.getBody().getMessage());
    }

    @Test
    @DisplayName("TestHandleHttpMessageNotReadableException_WhenThrown_ReturnsBadRequest")
    void testHandleHttpMessageNotReadableException() {
        // Arrange
        String specificMessage = "JSON parse error";
        HttpMessageNotReadableException exception = new HttpMessageNotReadableException(specificMessage);

        // Act
        ResponseEntity<ErrorResponse> response = advice.handleHttpMessageNotReadableException(exception);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Error de formato en el cuerpo de la solicitud: " + specificMessage, response.getBody().getMessage());
    }
}