package com.shopKpr.exceptions;

public class CategoryCreationException extends RuntimeException{
    public CategoryCreationException( String message ) {
        super(message);
    }
}
