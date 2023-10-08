package com.banksystem.Bank.exciption;

import com.banksystem.Bank.dto.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice("com.banksystem.Bank")
public class GlobalExceptionHandler{

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> InternalServerHandler(RuntimeException ex) {
        return ResponseEntity.status(400).body("Internal Server Error occoured\n" +  ex);

        }
    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<?> AccountNotFoundHandler(RuntimeException ex) {
        return ResponseEntity.status(404).body("Account Not Found\n" + ex.getMessage());

    }
    @ExceptionHandler(InvalidTransferAmountException.class)
    public ResponseEntity<?> InvalidTransferAmountHandler(RuntimeException ex) {
        return ResponseEntity.status(404).body("Invalid Transfer Amount\n" + ex);

    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorDetails> handleAccessDeniedException(AccessDeniedException exception,
                                                                    WebRequest webRequest){
        ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(),
                webRequest.getDescription(true));
        System.out.println(exception.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
    }
}


