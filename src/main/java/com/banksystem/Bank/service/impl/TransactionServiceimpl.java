package com.banksystem.Bank.service.impl;

import com.banksystem.Bank.dto.*;
import com.banksystem.Bank.entity.Account;
import com.banksystem.Bank.entity.Customer;
import com.banksystem.Bank.entity.Transaction;
import com.banksystem.Bank.exciption.AccountNotFoundException;
import com.banksystem.Bank.exciption.InsufficientBalanceException;
import com.banksystem.Bank.exciption.InvalidTransferAmountException;
import com.banksystem.Bank.repository.AccountRepository;
import com.banksystem.Bank.repository.CustomerRepository;
import com.banksystem.Bank.repository.TransactionRepository;
import com.banksystem.Bank.repository.TransactionSpec;
import com.banksystem.Bank.service.TransactionService;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class TransactionServiceimpl implements TransactionService {
    private AccountRepository accountRepository;
    private TransactionRepository transactionRepository;
    private CustomerRepository customerRepository;

    public TransactionServiceimpl(AccountRepository accountRepository, TransactionRepository transactionRepository, CustomerRepository customerRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public void moneyTransfer(long userId, TransactionRequestDTO transactionRequestDTO)  {
        Customer user = customerRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Optional<Account> senderAccount = accountRepository.findByCustomerAndAccountNumber(user,transactionRequestDTO.getSenderAccounNumber());
        int max = 999999;
        int min = 100000;
        Random rnd=new Random();
        String code = String.valueOf(rnd.nextInt((max - min) + 1) + min);
        LocalDate creationDate = LocalDate.now();
        LocalDateTime creationDateTime = LocalDateTime.now();
        if (senderAccount.isEmpty()) {
            throw new AccountNotFoundException("Sender account not found");
        }
        Account recieverAccount=accountRepository.findByAccountNumber(transactionRequestDTO.getRecieverAccountNumber());
        if (recieverAccount == null) {
            throw new AccountNotFoundException("Receiver account not found");
        }
        if(!recieverAccount.getCustomer().getId().equals(user.getId()))
            throw new AccountNotFoundException("Receiver account not found");

        BigDecimal amount=transactionRequestDTO.getAmount();
        if(amount.compareTo(BigDecimal.ZERO) <= 0)
        {
            throw new InvalidTransferAmountException("Invalid transfer amount");
        }
        if (senderAccount.get().getBalance().compareTo(amount)<0) {
            throw new InsufficientBalanceException("Insufficient balance in the sender account");
        }
        senderAccount.get().setBalance(senderAccount.get().getBalance().subtract(amount));
        recieverAccount.setBalance(recieverAccount.getBalance().add(amount));
        accountRepository.save(senderAccount.get());
        accountRepository.save(recieverAccount);
        Transaction transaction=new Transaction();
        transaction.setAmount(amount);
        transaction.setSenderAccount(senderAccount.get());
        transaction.setReceiverAccount(recieverAccount);
        transaction.setCode(code);
        transaction.setCreationDate(creationDate);
        transaction.setCreationDateTime(creationDateTime);
        transactionRepository.save(transaction);

    }

    @Override
    public List<TransactionDTO> getAllTransactionsBySenderORRecieverAccounNumber(long userId,TransactionRequestDTO transactionRequestDTO) {
        Customer user = customerRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        List<Account> userAccounts=user.getAccounts();
        boolean senderAccountBelongsToUser=userAccounts.stream().anyMatch(account -> account.getAccountNumber().equals(transactionRequestDTO.getSenderAccounNumber()));
        boolean receiverAccountBelongsToUser = userAccounts.stream()
                .anyMatch(account -> account.getAccountNumber().equals(transactionRequestDTO.getRecieverAccountNumber()));
        if (!senderAccountBelongsToUser || !receiverAccountBelongsToUser) {
            throw new IllegalArgumentException("Account numbers do not belong to the user");
        }
        List<Transaction> transactions = transactionRepository.findBySenderAccount_AccountNumberOrReceiverAccount_AccountNumber(transactionRequestDTO.getSenderAccounNumber(),transactionRequestDTO.getRecieverAccountNumber());
        List<TransactionDTO> transactionResponses = new ArrayList<>();
        for (Transaction transaction : transactions) {
            TransactionDTO transactionResponse = new TransactionDTO();
            transactionResponse.setId(transaction.getId());
            transactionResponse.setCode(transaction.getCode());
            transactionResponse.setCreationDateTime(transaction.getCreationDateTime());
            transactionResponse.setSenderAccountName(transaction.getSenderAccount().getName());
            transactionResponse.setSenderAccountNumber(transaction.getSenderAccount().getAccountNumber());
            transactionResponse.setRecieverAccountName(transaction.getReceiverAccount().getName());
            transactionResponse.setRecieverAccountNumber(transaction.getReceiverAccount().getAccountNumber());
            transactionResponse.setAmount(transaction.getAmount());
            transactionResponses.add(transactionResponse);
        }
        return transactionResponses;
    }

    @Override
    public List<TransactionDTO> SearchTransactions(long userId,SearchTransactionsDTO searchTransactionsDTO) {

        Customer user = customerRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        //all accounts for user
        List<Account> userAccounts=accountRepository.findByCustomer(user);
        List<Transaction> transactions= new ArrayList<Transaction>();
        if(searchTransactionsDTO.getCreationDateFrom() == null && searchTransactionsDTO.getCreationDateTo() == null
                && searchTransactionsDTO.getSenderAccountNumber() == null && searchTransactionsDTO.getRecieverAccountNumber() == null
                && searchTransactionsDTO.getCode() == null && searchTransactionsDTO.getAmount() == null)
        {
            List<Transaction> userTransactions = new ArrayList<>();
            for (Account account : userAccounts) {
//                userTransactions.addAll(transactionRepository.findBySenderAccountOrReceiverAccount(account, account));
                userTransactions.addAll(transactionRepository.findBySenderAccount(account));
            }
            transactions=userTransactions;
        }
        else {
            TransactionSpec transactionSpec=new TransactionSpec(searchTransactionsDTO,userAccounts);
            List<Transaction> SpecTransactions=transactionRepository.findAll(transactionSpec);
            transactions=SpecTransactions;
        }
        List<TransactionDTO> transactionResponses = new ArrayList<>();
        for (Transaction transaction : transactions){
                TransactionDTO transactionDTO=mapToDTO(transaction);
                transactionResponses.add(transactionDTO);
            }
            return transactionResponses;
    }

    private TransactionDTO mapToDTO (Transaction transaction){

        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setId(transaction.getId());
        transactionDTO.setCode(transaction.getCode());
        transactionDTO.setCreationDateTime(transaction.getCreationDateTime());
        transactionDTO.setSenderAccountName(transaction.getSenderAccount().getName());
        transactionDTO.setSenderAccountNumber(transaction.getSenderAccount().getAccountNumber());
        transactionDTO.setRecieverAccountName(transaction.getReceiverAccount().getName());
        transactionDTO.setRecieverAccountNumber(transaction.getReceiverAccount().getAccountNumber());
        transactionDTO.setAmount(transaction.getAmount());
        return transactionDTO;
    }

}


