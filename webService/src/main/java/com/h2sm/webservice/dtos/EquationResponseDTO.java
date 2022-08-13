package com.h2sm.webservice.dtos;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class EquationResponseDTO {
    private String formula;
    private double d;
    private double x1;
    private double x2;
}
