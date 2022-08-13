package com.h2sm.webservice.controller;

import com.h2sm.webservice.dtos.EquationRequestDTO;
import com.h2sm.webservice.service.RequestService;
import com.h2sm.webservice.dtos.EquationResponseDTO;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class EquationController {
    private final RequestService requestService;

    @RequestMapping(value = "/api/calc", method = RequestMethod.GET)
    public EquationResponseDTO returnEquationAnswer(@RequestParam int a,
                                                    @RequestParam int b,
                                                    @RequestParam int c){

       return requestService.makeRequest(new EquationRequestDTO(a,b,c));
    }
}
