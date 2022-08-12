package com.h2sm.soapservice.exceptions;

import com.h2sm.testtask.objects.EquationResponse;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.message.FormattedMessage;

public class DiscriminantLessThanZeroException extends Exception {

    public DiscriminantLessThanZeroException(EquationResponse response, int formula){
        super("Discriminant is less than zero: " + response.getD() + ", " + formula);
    }

}
