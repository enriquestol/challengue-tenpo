package com.example.challengue_tenpo.service.imp;

import com.example.challengue_tenpo.dto.CalcRequest;
import com.example.challengue_tenpo.dto.CalcResponse;
import com.example.challengue_tenpo.dto.CallLogResponse;
import com.example.challengue_tenpo.exception.CalculateException;
import com.example.challengue_tenpo.model.CallLog;
import com.example.challengue_tenpo.repository.CallLogRepository;
import com.example.challengue_tenpo.repository.repositoryLayer.CallLogDataAcces;
import com.example.challengue_tenpo.service.CalculateService;
import com.example.challengue_tenpo.service.PercentageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CalculateServiceImpl implements CalculateService {



    private final CallLogDataAcces callLogDataAcces;
    private final PercentageService percentageService;
    private final CacheManager cacheManager;


    @Override
    public CalcResponse calculate(CalcRequest request) {
        Double percentage = getCachedPercentage();
        if (percentage == null) {
            throw new CalculateException("No se pudo obtener el porcentaje y no hay valor en caché.");
        }

        Double result = (request.getNum1() + request.getNum2()) * (1 + percentage / 100);

        // Registrar la llamada de forma asíncrona
        logAsync("/api/v1/calculate", request.toString(), result.toString());

        CalcResponse response = new CalcResponse();
        response.setResult(result);
        response.setPercentageUsed(percentage);
        return response;
    }

    @Cacheable(value = "percentage", key = "'value'", unless = "#result == null")
    public Double getCachedPercentage() {
        try {
            return percentageService.getPercentage();
        } catch (Exception e) {
            Cache cache = cacheManager.getCache("percentage");
            if (cache != null) {
                Double cachedPercentage = cache.get("value", Double.class);
                if (cachedPercentage != null) {
                    return cachedPercentage;
                }
            }
            throw new CalculateException("Error al obtener el porcentaje y no hay valor en caché.", e);
        }
    }

    // Implement the getHistory method
    @Override
    public List<CallLogResponse> getHistory() {
        return callLogDataAcces.findAll().stream()
                .map(callLog -> new CallLogResponse(
                        callLog.getEndpoint(),
                        callLog.getParameters(),
                        callLog.getResponse(),
                        callLog.getTimestamp()
                ))
                .collect(Collectors.toList());
    }


    public void logAsync(String endpoint, String parameters, String response) {
        try {
            callLogDataAcces.save(new CallLog(null, endpoint, parameters, response, LocalDateTime.now()));
        } catch (Exception e) {
            throw new CalculateException("Error inesperado al guardar el registro de la llamada", e);
        }
    }

}
