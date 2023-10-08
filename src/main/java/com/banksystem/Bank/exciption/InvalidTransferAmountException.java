package com.banksystem.Bank.exciption;

public class InvalidTransferAmountException extends RuntimeException{
    public InvalidTransferAmountException(String message){

        super(message);
    }
}
