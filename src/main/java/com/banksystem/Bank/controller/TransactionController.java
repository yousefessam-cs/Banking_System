package com.banksystem.Bank.controller;

import com.banksystem.Bank.dto.SearchTransactionsDTO;
import com.banksystem.Bank.dto.TransactionDTO;
import com.banksystem.Bank.dto.TransactionRequestDTO;
import com.banksystem.Bank.entity.Account;
import com.banksystem.Bank.entity.Customer;
import com.banksystem.Bank.repository.AccountRepository;
import com.banksystem.Bank.repository.CustomerRepository;
import com.banksystem.Bank.security.JwtTokenProvider;
import com.banksystem.Bank.service.AccountService;
import com.banksystem.Bank.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/account/transaction")
public class TransactionController {

    private AccountService accountService;
    private TransactionService transactionService;
    private JwtTokenProvider jwtTokenProvider;


    public TransactionController(AccountService accountService, TransactionService transactionService,JwtTokenProvider jwtTokenProvider) {
        this.accountService = accountService;
        this.transactionService = transactionService;
        this.jwtTokenProvider=jwtTokenProvider;
    }

    @PostMapping("/money-transfer")
    public ResponseEntity<?> moneyTransfer(@RequestHeader(name = "Authorization") String token,@RequestBody TransactionRequestDTO transactionRequestDTO)  {
        if (!accountService.isTokenAuthenticated(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired token.");
        }
        int userId = jwtTokenProvider.extractUserId(token);
        transactionService.moneyTransfer(userId,transactionRequestDTO);
        Map<String, String> response =  new HashMap<>();
        response.put("msg", "Money transferred successfully");

        return new ResponseEntity<>( response, HttpStatus.OK);
    }
    @GetMapping("/transactions")
    public ResponseEntity<?> getAllTransactionsByAccountId(@RequestHeader(name = "Authorization") String token,@RequestBody TransactionRequestDTO transactionRequestDTO) {
        if (!accountService.isTokenAuthenticated(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired token.");
        }
        int userId = jwtTokenProvider.extractUserId(token);
        List<TransactionDTO> transactions = transactionService.getAllTransactionsBySenderORRecieverAccounNumber(userId,transactionRequestDTO);
        return ResponseEntity.ok(transactions);
    }
    @PostMapping("/search")
    public ResponseEntity<?>searchTransactions(@RequestHeader(name = "Authorization") String token,@RequestBody SearchTransactionsDTO searchTransactionsDTO){
        if (!accountService.isTokenAuthenticated(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired token.");
        }
        int userId = jwtTokenProvider.extractUserId(token);
        List<TransactionDTO> transactions = transactionService.SearchTransactions(userId,searchTransactionsDTO);
        return ResponseEntity.ok(transactions);
    }

}
