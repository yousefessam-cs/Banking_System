package com.banksystem.Bank.service;

import com.banksystem.Bank.dto.SearchTransactionsDTO;
import com.banksystem.Bank.dto.TransactionDTO;
import com.banksystem.Bank.dto.TransactionRequestDTO;
import com.banksystem.Bank.entity.Account;

import java.util.List;

public interface TransactionService {
    void moneyTransfer(long userId,TransactionRequestDTO transactionRequestDTO) ;
    List<TransactionDTO> getAllTransactionsBySenderORRecieverAccounNumber(long userId,TransactionRequestDTO transactionRequestDTO);
    public List<TransactionDTO> SearchTransactions(long userId,SearchTransactionsDTO searchTransactionsDTO);


}
