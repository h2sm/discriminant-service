package com.h2sm.testtask.exceptions;

import lombok.AllArgsConstructor;

public class DiscriminantLessThanZeroException extends Exception {

    public DiscriminantLessThanZeroException(){
        super("Discriminant is less than zero, neporyadok");
    }

}
