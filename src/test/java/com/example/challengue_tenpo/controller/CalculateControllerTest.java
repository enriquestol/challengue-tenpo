package com.example.challengue_tenpo.controller;

import com.example.challengue_tenpo.dto.CalcRequest;
import com.example.challengue_tenpo.dto.CalcResponse;
import com.example.challengue_tenpo.dto.CallLogResponse;
import com.example.challengue_tenpo.service.CalculateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Tests para CalculateController")
@WebMvcTest(CalculateController.class)
class CalculateControllerTest {

    private CalculateController calculateController;

    @MockBean
    private CalculateService calculateService;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        calculateService = mock(CalculateService.class);
        calculateController = new CalculateController(calculateService);
    }


    @Test
    @DisplayName("TestCalculateController_WhenBodyIsOk_thenReturnStatus200")
    void testCalculate_WhenBodyIsOk_thenReturnStatus200() {
        // Arrange
        CalcRequest request = new CalcRequest(10.0, 5.0);
        CalcResponse response = new CalcResponse(15.0,10.0);
        when(calculateService.calculate(request)).thenReturn(response);

        // Act
        ResponseEntity<CalcResponse> result = calculateController.calculate(request);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(15.0, result.getBody().getResult());
    }

    @Test
    @DisplayName("TestCalculateController_WhenBodyIsNull_thenReturnStatus400")
    void testCalculate_WhenBodyIsNull_thenReturnStatus400() throws Exception {
        // Act & Assert
        mockMvc.perform(post("/api/v1/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("")) // Enviamos un cuerpo vacío
                .andExpect(status().isBadRequest());
    }



    @Test
    @DisplayName("TestGetHistory_WhenCalled_thenReturnStatus200")
    void testGetHistory_WhenCalled_thenReturnStatus200() {
        // Arrange
        LocalDateTime timeStamp = LocalDateTime.now();
        List<CallLogResponse> history = List.of(new CallLogResponse("endpoint1", "params1", "response1", timeStamp));
        when(calculateService.getHistory()).thenReturn(history);

        // Act
        ResponseEntity<List<CallLogResponse>> result = calculateController.getHistory(0, 10);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(1, result.getBody().size());
    }
}