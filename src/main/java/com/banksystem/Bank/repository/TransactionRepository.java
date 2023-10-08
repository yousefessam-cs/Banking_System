package com.banksystem.Bank.repository;

import com.banksystem.Bank.entity.Account;
import com.banksystem.Bank.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long>, JpaSpecificationExecutor<Transaction> {
    List<Transaction> findBySenderAccount_IdOrReceiverAccount_Id(long senderAccountId,long recieverAccountId);
    List<Transaction> findBySenderAccount(Account senderAccount);
    List<Transaction> findBySenderAccount_AccountNumberOrReceiverAccount_AccountNumber(String senderAccountNumber,String recieverAccountNumber);
    List<Transaction> findBySenderAccountOrReceiverAccount(Account senderAccount,Account recieverAccount);
    List<Transaction> findByCreationDateTimeBetween(LocalDateTime fromDate,LocalDateTime toDate);
    List<Transaction> findByCode(String code);


}


