package com.shopKpr.exceptions;

public class BrandsNotFoundException extends RuntimeException{
    public BrandsNotFoundException(Long id)
    {
        super("Sub category with id: "+id+" Not found while adding a new brand");
    }
}
