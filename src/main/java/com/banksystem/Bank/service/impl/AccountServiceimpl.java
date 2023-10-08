package com.banksystem.Bank.service.impl;

import com.banksystem.Bank.dto.AccountDTO;
import com.banksystem.Bank.dto.UpdateAccountDTO;
import com.banksystem.Bank.dto.UserAccountDto;
import com.banksystem.Bank.entity.*;
import com.banksystem.Bank.repository.AccountRepository;
import com.banksystem.Bank.repository.BankRepository;
import com.banksystem.Bank.repository.CustomerRepository;
import com.banksystem.Bank.security.JwtTokenProvider;
import com.banksystem.Bank.service.AccountService;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

import javax.swing.text.html.Option;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountServiceimpl implements AccountService {
     AccountRepository accountRepository;
     JwtTokenProvider jwtTokenProvider;
     BankRepository bankRepository;
     CustomerRepository customerRepository;

    @Value("${app.jwt-secret}")
    private String SECRET_KEY;

    public AccountServiceimpl(AccountRepository accountRepository, BankRepository bankRepository, JwtTokenProvider jwtTokenProvider,CustomerRepository customerRepository) {
        this.accountRepository = accountRepository;
        this.bankRepository = bankRepository;
        this.jwtTokenProvider=jwtTokenProvider;
        this.customerRepository=customerRepository;

    }

    @Override
    public String createAccount(long userId, AccountDTO accountDTO)  {
       Customer customer = customerRepository.findById(userId).orElseThrow(()-> new IllegalArgumentException("User not found"));;
        Bank userBank=customer.getBank();
        if(userBank.getId()!=accountDTO.getBankId())
        {
            throw new RuntimeException("Can't Create Account with this BankID");
        }
        List<Account> userAccounts=accountRepository.findByCustomer(customer);
        Account checkAccount=accountRepository.findByAccountNumber(accountDTO.getAccountNumber());

        for (Account account : userAccounts) {
            if (account.getAccountNumber().equals(accountDTO.getAccountNumber())||checkAccount!=null) {
                throw new IllegalArgumentException("Can't Create Account with this Account Number");
            }
        }

        if (userBank==null && customer==null)
            return "Fail To Save Account";
//        List<CustomerBanks> customerBankUser=customerBanksRepository.findAllByBankIdAndCustomerId(accountDTO.getBankId(),userId);
//            if(customerBankUser.isEmpty()){
//                CustomerBanks customerBank = new CustomerBanks();
////                customerBank.setBank(userBank.get());
////                customerBank.setCustomer(customer.get());
//                customerBanksRepository.save(customerBank);
//            }

        Account account = mapToEntity(accountDTO, userBank, customer);
        accountRepository.save(account);
        return "Saved Successfully";
    }

    @Override
    public UserAccountDto getAccountById(long userId, long accountId) {
        Customer user = customerRepository.findById((long) userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Account account=accountRepository.findByCustomerAndId(user,accountId).orElseThrow(() -> new RuntimeException("Account with id " + accountId + " Doesn't Exist"));
        UserAccountDto accountResponse=new UserAccountDto();
        accountResponse.setId(account.getId());
        accountResponse.setName(account.getName());
        accountResponse.setAccountNumber(account.getAccountNumber());
        accountResponse.setType(account.getType());
        accountResponse.setBalance(account.getBalance());
        return accountResponse;
    }


    @Override
    public List<UserAccountDto> getAllAccounts(int userId) {

        Customer user = customerRepository.findById((long) userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        List<Account> userAccounts = accountRepository.findByCustomer(user);
        List<UserAccountDto> userAccountResponses = new ArrayList<>();
        for (Account account : userAccounts) {
            UserAccountDto userAccountDto=new UserAccountDto();
            userAccountDto.setId(account.getId());
            userAccountDto.setName(account.getName());
            userAccountDto.setAccountNumber(account.getAccountNumber());
            userAccountDto.setType(account.getType());
            userAccountDto.setBalance(account.getBalance());
            userAccountResponses.add(userAccountDto);
        }
        return userAccountResponses;
    }

    @Override
    public UserAccountDto updateAccount(int userId, long accountId, UpdateAccountDTO updateAccountDTO) {
        Customer user = customerRepository.findById((long) userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Account userAccount=accountRepository.findByCustomerAndId(user,accountId).orElseThrow(() -> new IllegalArgumentException("Account not found"));
        List<Account> userAccounts=accountRepository.findByCustomer(user);
        Account checkAccount=accountRepository.findByAccountNumber(updateAccountDTO.getAccountNumber());

        for (Account account : userAccounts) {
            if (account.getAccountNumber().equals(updateAccountDTO.getAccountNumber())||checkAccount!= null) {
                throw new IllegalArgumentException("Can't Create Account with this Account Number");
            }
        }
        userAccount.setName(updateAccountDTO.getName());
        userAccount.setAccountNumber(updateAccountDTO.getAccountNumber());
        accountRepository.save(userAccount);
        UserAccountDto userAccountDto = new UserAccountDto();
        userAccountDto.setId(userAccount.getId());
        userAccountDto.setName(updateAccountDTO.getName());
        userAccountDto.setAccountNumber(updateAccountDTO.getAccountNumber());
        userAccountDto.setType(userAccount.getType());
        userAccountDto.setBalance(userAccount.getBalance());
        return userAccountDto;
    }

    @Override
    public UserAccountDto updateAccountBalance(int userId, long accountId, UpdateAccountDTO updateAccountDTO) {
        Customer user = customerRepository.findById((long) userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Account account=accountRepository.findByCustomerAndId(user,accountId).orElseThrow(() -> new IllegalArgumentException("Account not found"));
        account.setBalance(updateAccountDTO.getBalance());
        accountRepository.save(account);
        UserAccountDto userAccountDto = new UserAccountDto();
        userAccountDto.setId(account.getId());
        userAccountDto.setName(account.getName());
        userAccountDto.setAccountNumber(account.getAccountNumber());
        userAccountDto.setType(account.getType());
        userAccountDto.setBalance(updateAccountDTO.getBalance());
        return userAccountDto;
    }


//    @Override
//    public void deleteAccountById(long accountId) {
//        Account account = accountRepository.findById(accountId).orElseThrow(
//                () -> new ResourceNotFoundException("account", "accountId", accountId));
//        accountRepository.delete(account);
//    }


    private AccountDTO mapToDTO (Account account){


        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(account.getId());
        accountDTO.setAccountNumber(account.getAccountNumber());
        accountDTO.setName(account.getName());
        accountDTO.setType(account.getType());
        accountDTO.setBankId(account.getBank().getId());
       accountDTO.setCustomer(account.getCustomer());
        return accountDTO;
    }

    @Override
    public boolean isTokenAuthenticated(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            //System.out.println("Error");
            return false;
        }
    }

    private Account mapToEntity(AccountDTO accountDTO, Bank bank, Customer customer) {
        Account account = new Account();
        account.setName(accountDTO.getName());
        account.setBalance(accountDTO.getBalance());
        account.setAccountNumber(accountDTO.getAccountNumber());
        account.setType(accountDTO.getType());
        account.setBank(bank);
        account.setCustomer(customer);
        return account;
    }
}
