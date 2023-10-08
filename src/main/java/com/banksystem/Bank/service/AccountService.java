package com.banksystem.Bank.service;
import com.banksystem.Bank.dto.AccountDTO;
import com.banksystem.Bank.dto.UpdateAccountDTO;
import com.banksystem.Bank.dto.UserAccountDto;

import java.util.List;

public interface AccountService {
    String createAccount(long userId, AccountDTO accountDto);
    UserAccountDto getAccountById(long userId, long accountId);
    boolean isTokenAuthenticated(String token);

    List<?> getAllAccounts(int userId);
   UserAccountDto updateAccount(int userId, long accountId, UpdateAccountDTO updateAccountDTO);
    UserAccountDto updateAccountBalance(int userId, long accountId, UpdateAccountDTO updateAccountDTO);



}
