package com.shopKpr.exceptions;

public class SubCategoryNotFound extends RuntimeException{
    public SubCategoryNotFound(Long id)
    {
        super("Sub category with id: "+id+" Not found while adding a new brand");
    }
}
