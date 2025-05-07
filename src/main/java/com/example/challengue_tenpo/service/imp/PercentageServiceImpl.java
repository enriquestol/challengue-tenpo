package com.example.challengue_tenpo.service.imp;

import com.example.challengue_tenpo.service.PercentageService;

public class PercentageServiceImpl implements PercentageService {

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
