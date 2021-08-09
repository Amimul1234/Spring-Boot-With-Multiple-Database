package com.shopKpr.exceptions;

public class ProductNotFoundException extends RuntimeException{
    public ProductNotFoundException(Long id)
    {
        super("Product with id: "+id+" Not found");
    }
}
