package com.shopKpr.exceptions;

public class ShopNotApproved extends RuntimeException{
    public ShopNotApproved( String message ) {
        super(message);
    }
}
