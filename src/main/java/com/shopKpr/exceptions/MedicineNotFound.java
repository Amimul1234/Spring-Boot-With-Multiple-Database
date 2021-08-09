package com.shopKpr.exceptions;

public class MedicineNotFound extends RuntimeException{
    public MedicineNotFound( String message ) {
        super(message);
    }
}
