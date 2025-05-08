package com.example.challengue_tenpo.service;

import com.example.challengue_tenpo.dto.CalcRequest;
import com.example.challengue_tenpo.dto.CalcResponse;
import com.example.challengue_tenpo.dto.CallLogResponse;

import java.util.List;

public interface CalculateService {
    CalcResponse calculate(CalcRequest request);
    List<CallLogResponse> getHistory();
}
