package com.shopKpr.exceptions;

public class EmailAddressMismatch extends RuntimeException{
    public EmailAddressMismatch( String message ) {
        super(message);
    }
}
