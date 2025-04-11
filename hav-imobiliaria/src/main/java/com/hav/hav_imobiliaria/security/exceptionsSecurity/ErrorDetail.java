package com.hav.hav_imobiliaria.security.exceptionsSecurity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDetail {

    private String error;
    private String message;
    private LocalDateTime timestamp;
}
