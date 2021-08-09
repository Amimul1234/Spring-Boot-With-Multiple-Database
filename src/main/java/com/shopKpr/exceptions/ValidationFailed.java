package com.shopKpr.exceptions;

public class ValidationFailed extends RuntimeException{
    public ValidationFailed( String message ) {
        super(message);
    }
}
