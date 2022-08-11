package com.h2sm.testtask.endpoint;

import com.h2sm.testtask.exceptions.DiscriminantLessThanZeroException;
import com.h2sm.testtask.objects.EquationRequest;
import com.h2sm.testtask.objects.EquationResponse;
import com.h2sm.testtask.services.CounterService;
import lombok.RequiredArgsConstructor;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
@RequiredArgsConstructor
public class EquationEndpoint {
    public static final String URI = "/count";
    public static final String LOCAL_PART = "EquationRequest";
    private final CounterService counterService;

    @PayloadRoot(namespace = URI, localPart = LOCAL_PART)
    @ResponsePayload
    public EquationResponse getResponse(@RequestPayload EquationRequest equationRequest) {
        return counterService.countResults(equationRequest);
    }
}
