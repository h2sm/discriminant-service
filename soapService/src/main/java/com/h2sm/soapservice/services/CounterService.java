package com.h2sm.soapservice.services;

import com.h2sm.soapservice.exceptions.DiscriminantLessThanZeroException;
import com.h2sm.testtask.objects.EquationRequest;
import com.h2sm.testtask.objects.EquationResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CounterService {

    public EquationResponse countResults(EquationRequest request) throws DiscriminantLessThanZeroException {
        var response = new EquationResponse();
            makeStringFormula(request, response);
            countDiscriminant(request, response);
            setRoots(request, response);
        return response;
    }

    private void countDiscriminant(EquationRequest request, EquationResponse response)
            throws DiscriminantLessThanZeroException {
        var discriminant = request.getB() * request.getB() - 4 * request.getA() * request.getC();
        response.setD(discriminant);
        if (discriminant < 0) {
            throw new DiscriminantLessThanZeroException(response);
        }
    }

    private void makeStringFormula(EquationRequest request, EquationResponse response) {
        var builder = new StringBuilder();
        builder.append(request.getA() + "x^2");

        if (request.getB() > 0) {
            builder.append("+" + request.getB() + "x");
        } else if (request.getB() == 0) {
            builder.append("+x");
        } else {
            builder.append(request.getB() + "x");
        }

        if (request.getC() >= 0) {
            builder.append("+" + request.getC());
        } else {
            builder.append("-" + request.getC());
        }

        response.setFormula(builder.append("=0").toString());
    }

    private void setRoots(EquationRequest request, EquationResponse response) {
        if (response.getD() == 0) {
            response.setX1(-request.getB() / 2.0 * request.getA());
            response.setX2(0);
        } else {
            response.setX1((-request.getB() + Math.sqrt(response.getD())) / 2 * request.getA());
            response.setX2((-request.getB() - Math.sqrt(response.getD())) / 2 * request.getA());
        }
    }
}
