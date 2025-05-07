package com.example.challengue_tenpo.controller;

import com.example.challengue_tenpo.dto.CalcRequest;
import com.example.challengue_tenpo.dto.CalcResponse;
import com.example.challengue_tenpo.service.CalculateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1")
public class CalculateController {

    private final CalculateService calculateService;

    public CalculateController(CalculateService calculateService) {
        this.calculateService = calculateService;
    }


    @PostMapping("/calculate")
    public ResponseEntity<CalcResponse> calculate(@RequestBody CalcRequest request) {
        return ResponseEntity.ok(calculateService.calculate(request));
    }

        @GetMapping("/history")
        public ResponseEntity<?> getHistory(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
            return ResponseEntity.ok(calculateService.getHistory());
        }
    }

