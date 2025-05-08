package com.example.challengue_tenpo.service.imp;

import com.example.challengue_tenpo.service.PercentageService;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class PercentageServiceImpl implements PercentageService {

    private final WebClient webClient;

    public PercentageServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://mock-service").build();
    }

    @Override
    public Double getPercentage() {
        return webClient.get()
                .uri("/percentage")
                .retrieve()
                .bodyToMono(Double.class)
                .onErrorReturn(30.0)
                .block();
    }
}
