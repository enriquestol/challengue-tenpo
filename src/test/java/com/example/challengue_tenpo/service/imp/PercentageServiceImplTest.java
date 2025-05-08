package com.example.challengue_tenpo.service.imp;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.web.reactive.function.client.WebClient;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Tests para PercentageServiceImpl con WireMock")
class PercentageServiceImplWireMockTest {

    @RegisterExtension
    static WireMockExtension wireMockServer = WireMockExtension.newInstance().build();

    private PercentageServiceImpl percentageService;

    @BeforeEach
    void setUp() {
        WebClient.Builder webClientBuilder = WebClient.builder()
                .baseUrl(wireMockServer.baseUrl());
        percentageService = new PercentageServiceImpl(webClientBuilder);
    }

    @Test
    @DisplayName("TestGetPercentage_WhenServiceReturnsValue_thenReturnCorrectValue")
    void testGetPercentage_WhenServiceReturnsValue_thenReturnCorrectValue() {
        // Arrange
        wireMockServer.stubFor(get(urlEqualTo("/percentage"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody("30.0")));

        // Act
        Double percentage = percentageService.getPercentage();

        // Assert
        assertEquals(30.0, percentage);
    }

    @Test
    @DisplayName("TestGetPercentage_WhenServiceFails_thenReturnDefaultValue")
    void testGetPercentage_WhenServiceFails_thenReturnDefaultValue() {
        // Arrange
        wireMockServer.stubFor(get(urlEqualTo("/percentage"))
                .willReturn(aResponse()
                        .withStatus(500)));

        // Act
        Double percentage = percentageService.getPercentage();

        // Assert
        assertEquals(30.0, percentage); // Valor por defecto
    }
}