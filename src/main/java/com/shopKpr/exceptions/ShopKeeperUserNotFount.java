package com.shopKpr.exceptions;

public class ShopKeeperUserNotFount extends RuntimeException{
    public ShopKeeperUserNotFount(String mobileNumber)
    {
        super("Shop Keeper with mobile number: "+mobileNumber+" Not found");
    }
}
