package com.shopKpr.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AlreadyExists.class)
    public ResponseEntity<ExceptionResponse> resourceAlreadyExists( AlreadyExists ex ) {
        ExceptionResponse response = new ExceptionResponse();

        response.setErrorCode("CONFLICT");
        response.setErrorMessage(ex.getMessage());
        response.setTimestamp(LocalDateTime.now());

        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<ExceptionResponse> disabledException( DisabledException ex ) {
        ExceptionResponse response = new ExceptionResponse();

        response.setErrorCode("FORBIDDEN");
        response.setErrorMessage(ex.getMessage());
        response.setTimestamp(LocalDateTime.now());

        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ExceptionResponse> badCredentialException( BadCredentialsException ex ) {
        ExceptionResponse response = new ExceptionResponse();

        response.setErrorCode("UNAUTHORIZED");
        response.setErrorMessage(ex.getMessage());
        response.setTimestamp(LocalDateTime.now());

        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ImageCreationException.class)
    public ResponseEntity<ExceptionResponse> imageCreationFailed( ImageCreationException ex ) {
        ExceptionResponse response = new ExceptionResponse();

        response.setErrorCode("FAILED");
        response.setErrorMessage(ex.getMessage());
        response.setTimestamp(LocalDateTime.now());

        return new ResponseEntity<>(response, HttpStatus.FAILED_DEPENDENCY);
    }

    @ExceptionHandler(MobileNumberMisMatch.class)
    public ResponseEntity<ExceptionResponse> mobileMismatch( MobileNumberMisMatch ex ) {
        ExceptionResponse response = new ExceptionResponse();

        response.setErrorCode("Wrong Number");
        response.setErrorMessage(ex.getMessage());
        response.setTimestamp(LocalDateTime.now());

        return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(EmailAddressMismatch.class)
    public ResponseEntity<ExceptionResponse> mobileMismatch( EmailAddressMismatch ex ) {
        ExceptionResponse response = new ExceptionResponse();

        response.setErrorCode("Wrong Email Address");
        response.setErrorMessage(ex.getMessage());
        response.setTimestamp(LocalDateTime.now());

        return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(CategoryCreationException.class)
    public ResponseEntity<ExceptionResponse> categoryCreationException( CategoryCreationException categoryCreationException ) {
        ExceptionResponse response = new ExceptionResponse();

        response.setErrorCode("Can not create category");
        response.setErrorMessage(categoryCreationException.getMessage());
        response.setTimestamp(LocalDateTime.now());

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ExceptionResponse> categoryNotFoundException( CategoryNotFoundException categoryNotFoundException ) {
        ExceptionResponse response = new ExceptionResponse();

        response.setErrorCode("Category not found");
        response.setErrorMessage(categoryNotFoundException.getMessage());
        response.setTimestamp(LocalDateTime.now());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MedicineNotFound.class)
    public ResponseEntity<ExceptionResponse> medicineNotFound( MedicineNotFound medicineNotFound ) {
        ExceptionResponse response = new ExceptionResponse();

        response.setErrorCode("MedicineAdmin not found");
        response.setErrorMessage(medicineNotFound.getMessage());
        response.setTimestamp(LocalDateTime.now());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DosageFormNotFound.class)
    public ResponseEntity<ExceptionResponse> medicineNotFound( DosageFormNotFound dosageFormNotFound ) {
        ExceptionResponse response = new ExceptionResponse();

        response.setErrorCode("Dosage form not found");
        response.setErrorMessage(dosageFormNotFound.getMessage());
        response.setTimestamp(LocalDateTime.now());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ValidationFailed.class)
    public ResponseEntity<ExceptionResponse> unauthorizedException( ValidationFailed ex ) {

        ExceptionResponse response = new ExceptionResponse();

        response.setErrorCode("Data Invalid");
        response.setErrorMessage(ex.getMessage());
        response.setTimestamp(LocalDateTime.now());

        return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(ShopAlreadyExists.class)
    public ResponseEntity<ExceptionResponse> shopAlreadyExists( ShopAlreadyExists ex ) {

        ExceptionResponse response = new ExceptionResponse();

        response.setErrorCode("Shop Exists");
        response.setErrorMessage(ex.getMessage());
        response.setTimestamp(LocalDateTime.now());

        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ShopNotApproved.class)
    public ResponseEntity<ExceptionResponse> shopNotApproved( ShopNotApproved ex ) {

        ExceptionResponse response = new ExceptionResponse();

        response.setErrorCode("Shop Not Approved");
        response.setErrorMessage(ex.getMessage());
        response.setTimestamp(LocalDateTime.now());

        return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(ShopKprNotFound.class)
    public ResponseEntity<ExceptionResponse> shopNotApproved( ShopKprNotFound ex ) {

        ExceptionResponse response = new ExceptionResponse();

        response.setErrorCode("Shop keeper not found");
        response.setErrorMessage(ex.getMessage());
        response.setTimestamp(LocalDateTime.now());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ShopNotFoundException.class)
    public ResponseEntity<ExceptionResponse> shopNotFound( ShopNotFoundException ex ) {

        ExceptionResponse response = new ExceptionResponse();

        response.setErrorCode("Shop not found");
        response.setErrorMessage(ex.getMessage());
        response.setTimestamp(LocalDateTime.now());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BannerCreationException.class)
    public ResponseEntity<ExceptionResponse> shopNotFound( BannerCreationException ex ) {

        ExceptionResponse response = new ExceptionResponse();

        response.setErrorCode("Can not create banner");
        response.setErrorMessage(ex.getMessage());
        response.setTimestamp(LocalDateTime.now());

        return new ResponseEntity<>(response, HttpStatus.FAILED_DEPENDENCY);
    }

    @ExceptionHandler(BannerEnableException.class)
    public ResponseEntity<ExceptionResponse> shopNotFound( BannerEnableException ex ) {

        ExceptionResponse response = new ExceptionResponse();

        response.setErrorCode("Can not enable banner");
        response.setErrorMessage(ex.getMessage());
        response.setTimestamp(LocalDateTime.now());

        return new ResponseEntity<>(response, HttpStatus.FAILED_DEPENDENCY);
    }


    @ExceptionHandler(UnAuthorizedException.class)
    public ResponseEntity<ExceptionResponse> unauthorizedException( UnAuthorizedException ex ) {

        ExceptionResponse response = new ExceptionResponse();

        response.setErrorCode("UNAUTHORIZED");
        response.setErrorMessage(ex.getMessage());
        response.setTimestamp(LocalDateTime.now());

        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }
}
