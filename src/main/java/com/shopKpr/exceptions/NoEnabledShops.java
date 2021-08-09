package com.shopKpr.exceptions;

public class NoEnabledShops extends RuntimeException{
    public NoEnabledShops()
    {
        super("No enabled shop found");
    }
}
