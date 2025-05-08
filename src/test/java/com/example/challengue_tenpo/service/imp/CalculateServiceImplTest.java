package com.example.challengue_tenpo.service.imp;

import com.example.challengue_tenpo.dto.CalcRequest;
import com.example.challengue_tenpo.dto.CalcResponse;
import com.example.challengue_tenpo.dto.CallLogResponse;
import com.example.challengue_tenpo.exception.CalculateException;
import com.example.challengue_tenpo.model.CallLog;
import com.example.challengue_tenpo.repository.repositoryLayer.CallLogDataAcces;
import com.example.challengue_tenpo.service.PercentageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@DisplayName("Tests para CalculateServiceImpl")
class CalculateServiceImplTest {

    private CalculateServiceImpl calculateService;


    @Mock
    private PercentageService percentageService;

    @Mock
    private CacheManager cacheManager;

    @Mock
    private Cache cache;

    @Mock
    private CallLogDataAcces callLogDataAcces; // Mock de CallLogDataAcces

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        calculateService = new CalculateServiceImpl(callLogDataAcces, percentageService, cacheManager);
    }

    @Test
    @DisplayName("TestCalculate_WhenPercentageIsAvailable_thenReturnCorrectResult")
    void testCalculate_WhenPercentageIsAvailable_thenReturnCorrectResult() {
        // Arrange
        CalcRequest request = new CalcRequest(10.0, 5.0);
        when(percentageService.getPercentage()).thenReturn(10.0);

        // Act
        CalcResponse response = calculateService.calculate(request);

        // Assert
        assertEquals(16.5, response.getResult());
        assertEquals(10.0, response.getPercentageUsed());
    }

    @Test
    @DisplayName("TestCalculate_WhenPercentageIsNotAvailableAndCacheIsEmpty_thenThrowException")
    void testCalculate_WhenPercentageIsNotAvailableAndCacheIsEmpty_thenThrowException() {
        // Arrange
        CalcRequest request = new CalcRequest(10.0, 5.0);
        when(percentageService.getPercentage()).thenThrow(new RuntimeException("Error"));
        when(cacheManager.getCache("percentage")).thenReturn(cache);
        when(cache.get("value", Double.class)).thenReturn(null);

        // Act & Assert
        CalculateException exception = assertThrows(CalculateException.class, () -> calculateService.calculate(request));
        assertEquals("Error al obtener el porcentaje y no hay valor en caché.", exception.getMessage());
    }

    @Test
    @DisplayName("TestCalculate_WhenPercentageIsNotAvailableButCacheHasValue_thenUseCachedValue")
    void testCalculate_WhenPercentageIsNotAvailableButCacheHasValue_thenUseCachedValue() {
        // Arrange
        CalcRequest request = new CalcRequest(10.0, 5.0);
        when(percentageService.getPercentage()).thenThrow(new RuntimeException("Error"));
        when(cacheManager.getCache("percentage")).thenReturn(cache);
        when(cache.get("value", Double.class)).thenReturn(20.0);

        // Act
        CalcResponse response = calculateService.calculate(request);

        // Assert
        assertEquals(18.0, response.getResult());
        assertEquals(20.0, response.getPercentageUsed());
    }


    @Test
    @DisplayName("TestGetHistory_WhenLogsExist_thenReturnLogList")
    void testGetHistory_WhenLogsExist_thenReturnLogList() {
        // Arrange
        LocalDateTime timestamp = LocalDateTime.now();
        List<CallLog> logs = List.of(new CallLog(1L, "endpoint1", "params1", "response1", timestamp));
        when(callLogDataAcces.findAll()).thenReturn(logs);

        // Act
        List<CallLogResponse> history = calculateService.getHistory();

        // Assert
        assertEquals(1, history.size());
        assertEquals("endpoint1", history.get(0).getEndpoint());
        assertEquals("params1", history.get(0).getParameters());
        assertEquals("response1", history.get(0).getResponse());
        assertEquals(timestamp, history.get(0).getTimestamp());
    }

    @Test
    @DisplayName("TestGetHistory_WhenNoLogsExist_thenReturnEmptyList")
    void testGetHistory_WhenNoLogsExist_thenReturnEmptyList() {
        // Arrange
        when(callLogDataAcces.findAll()).thenReturn(List.of());

        // Act
        List<CallLogResponse> history = calculateService.getHistory();

        // Assert
        assertTrue(history.isEmpty());
    }
}