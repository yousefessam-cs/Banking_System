package com.banksystem.Bank.dto;

import com.banksystem.Bank.entity.Transaction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SearchTransactionsDTO {
    private LocalDate creationDateFrom;
    private LocalDate creationDateTo;
    private String code;
    private String senderAccountNumber;
    private String recieverAccountNumber;
    private BigDecimal amount;

//    private long senderAccountID;
//    private long recieverAccountID;
//    private List<Transaction> SentTransactions;
//    private List<Transaction> ReceivedTransactions;


}
