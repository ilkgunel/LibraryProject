package com.ilkaygunel.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String errorMessage = "Validation Error";

        List<ApiSubError> apiSubErrorList = new ArrayList<>();

        List<FieldError> fieldErrorList = ex.getBindingResult().getFieldErrors();
        for (FieldError fieldError : fieldErrorList) {
            String field = fieldError.getField();
            String rejectedValue = (String) fieldError.getRejectedValue();
            ApiValidationError apiValidationError = new ApiValidationError("book",fieldError.getDefaultMessage());
            apiValidationError.setField(field);
            apiValidationError.setRejectedValue(rejectedValue);
            apiSubErrorList.add(apiValidationError);
        }

        ApiError apiError = new ApiError(status, errorMessage, ex);
        apiError.setSubErrors(apiSubErrorList);
        return buildResponseEntity(apiError);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String errorMessage = "Internal Server Error!";
        ApiError apiError = new ApiError(status, errorMessage, ex);
        return buildResponseEntity(apiError);
    }

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity(apiError, apiError.getStatus());
    }
}
