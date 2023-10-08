package com.banksystem.Bank.exciption;

public class AccountNotFoundException extends RuntimeException{
    public AccountNotFoundException(String message){

        super(message);
    }
}
