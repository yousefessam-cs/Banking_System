package com.banksystem.Bank.dto;

import com.banksystem.Bank.entity.Account;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDTO {
    private Long id;
    private String code ;
    private LocalDateTime creationDateTime;
    private String senderAccountName;
    private String senderAccountNumber;
    private String recieverAccountName;
    private String recieverAccountNumber;
    private BigDecimal amount;
}
