package com.example.challengue_tenpo.service.imp;

import com.example.challengue_tenpo.dto.CalcRequest;
import com.example.challengue_tenpo.dto.CalcResponse;
import com.example.challengue_tenpo.model.CallLog;
import com.example.challengue_tenpo.repository.CallLogRepository;
import com.example.challengue_tenpo.repository.repositoryLayer.CallLogDataAcces;
import com.example.challengue_tenpo.service.CalculateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CalculateServiceImpl implements CalculateService {


    @Autowired
    private CallLogDataAcces callLogDataAcces;

    // Implement the calculate method
    @Override
    public CalcResponse calculate(CalcRequest request) {
        // Perform the calculation based on the request
        // For example, if it's a simple addition:
        Double result = request.getNum1() + request.getNum2();

        // Create and return the response
        CalcResponse response = new CalcResponse();
        response.setResult(result);
        return response;
    }

    // Implement the getHistory method
    @Override
    public List<String> getHistory() {
        // Retrieve and return the calculation history from the database
        return callLogDataAcces.findAll().stream()
                .map(CallLog::toString)
                .collect(Collectors.toList());
    }

    public void logAsync(String endpoint, String parameters, String response) {
        callLogDataAcces.save(new CallLog(null, endpoint, parameters, response));
    }

}
