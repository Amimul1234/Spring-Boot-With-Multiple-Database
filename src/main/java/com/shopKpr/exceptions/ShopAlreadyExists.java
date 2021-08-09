package com.shopKpr.exceptions;

public class ShopAlreadyExists extends RuntimeException{
    public ShopAlreadyExists( String message ) {
        super(message);
    }
}
