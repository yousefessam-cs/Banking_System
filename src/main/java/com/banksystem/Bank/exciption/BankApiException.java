package com.banksystem.Bank.exciption;

import org.springframework.http.HttpStatus;

public class BankApiException extends RuntimeException{
    private HttpStatus status;
    private String message;
    public BankApiException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
    public BankApiException(String message, HttpStatus status, String message1) {
        super(message);
        this.status = status;
        this.message = message1;
    }
    public HttpStatus getStatus() {
        return status;
    }
    @Override
    public String getMessage() {
        return message;
    }

}
