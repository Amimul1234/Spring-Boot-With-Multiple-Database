package com.shopKpr.exceptions;

public class BrandListNotAvailable extends RuntimeException{

    public BrandListNotAvailable()
    {
        super("No brand found on this sub-category");
    }
}
