package com.h2sm.webservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EquationResponseDTO {
    private String formula;
    private double d;
    private double x1;
    private double x2;
}
