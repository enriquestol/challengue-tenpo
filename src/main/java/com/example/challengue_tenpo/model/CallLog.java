package com.example.challengue_tenpo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "call_logs")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CallLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String endpoint;
    private String parameters;
    private String response;
    private LocalDateTime timestamp;

}
