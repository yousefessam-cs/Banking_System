package com.banksystem.Bank.controller;

import com.banksystem.Bank.dto.AccountDTO;
import com.banksystem.Bank.dto.UpdateAccountDTO;
import com.banksystem.Bank.dto.UserAccountDto;

import com.banksystem.Bank.security.JwtTokenProvider;
import com.banksystem.Bank.service.AccountService;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/account")
public class AccountController {
    private AccountService accountService;

    private JwtTokenProvider jwtTokenProvider;



    public AccountController(AccountService accountService,JwtTokenProvider jwtTokenProvider) {
        this.accountService = accountService;
        this.jwtTokenProvider=jwtTokenProvider;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createAccount(@RequestHeader(name = "Authorization") String token,@RequestBody AccountDTO accountDTO)  {

        if (!accountService.isTokenAuthenticated(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired token.");
        }

        int userId = jwtTokenProvider.extractUserId(token);
//        Integer bankIds = jwtTokenProvider.extractBankId(token);
        String createdAccount = accountService.createAccount(userId, accountDTO);

        return ResponseEntity.ok("Account created successfully.");


    }

    @GetMapping("/all")
    public List<?> getAllAccounts(@RequestHeader(name = "Authorization") String token){
//        String jwtToken=token.trim();
        if (!accountService.isTokenAuthenticated(token)) {
            return Collections.singletonList(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired token."));
        }
        int userId = jwtTokenProvider.extractUserId(token);
        return accountService.getAllAccounts(userId);
    }
    @GetMapping("/accounts/{accountId}")
    public ResponseEntity<?> getAccountById(@RequestHeader(name = "Authorization") String token,@PathVariable Long accountId) {
        if (!accountService.isTokenAuthenticated(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired token.");
        }
        int userId = jwtTokenProvider.extractUserId(token);
        UserAccountDto account = accountService.getAccountById(userId,accountId);
        return ResponseEntity.ok(account);
    }



    @PutMapping("/accounts/{accountId}")
    public ResponseEntity<?> updateAccount(@RequestHeader(name = "Authorization") String token,@PathVariable Long accountId,
                                                     @RequestBody UpdateAccountDTO updateRequest){
        if (!accountService.isTokenAuthenticated(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired token.");
        }
        int userId = jwtTokenProvider.extractUserId(token);
        UserAccountDto updatedAccount= accountService.updateAccount(userId,accountId,updateRequest);
        return ResponseEntity.ok(updatedAccount);

    }
    @PutMapping("/{accountId}/balance")
    public ResponseEntity<?> updateBalance(@RequestHeader(name = "Authorization") String token,@PathVariable Long accountId,
                                                @RequestBody UpdateAccountDTO updateAccountDTO) {

        if (!accountService.isTokenAuthenticated(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired token.");
        }
        int userId = jwtTokenProvider.extractUserId(token);
        UserAccountDto userAccountDto= accountService.updateAccountBalance(userId,accountId, updateAccountDTO);
        return ResponseEntity.ok(userAccountDto);
    }


//    @DeleteMapping("/accounts/{id}")
//    public ResponseEntity<String> deleteAccountById(@PathVariable(value = "accountId") Long accountId){
//        accountService.deleteAccountById(accountId);
//        return new ResponseEntity<>("Account deleted successfully", HttpStatus.OK);
//    }
//
 }

