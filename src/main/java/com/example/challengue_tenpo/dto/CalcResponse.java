package com.example.challengue_tenpo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CalcResponse {
    private Double result;
    private Double percentageUsed;
}
