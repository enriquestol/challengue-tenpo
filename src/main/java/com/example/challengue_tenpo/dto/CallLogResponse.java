package com.example.challengue_tenpo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class CallLogResponse{
    private String endpoint;
    private String parameters;
    private String response;
    private LocalDateTime timestamp;
}
