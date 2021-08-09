package com.shopKpr.exceptions;

public class NoOffersException extends RuntimeException{
    public NoOffersException() {
        super("No offers available");
    }
}
