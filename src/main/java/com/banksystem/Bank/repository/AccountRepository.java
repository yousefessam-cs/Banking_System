package com.banksystem.Bank.repository;

import com.banksystem.Bank.entity.Account;
import com.banksystem.Bank.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account,Long> {
    List<Account> findByBankId(Long bankId);
    List<Account> findByCustomer(Customer user);
    Optional<Account> findByCustomerAndId(Customer user ,Long accountId);
    Optional<Account> findByCustomerAndAccountNumber(Customer user ,String accountNumber);
    Optional<Account> findById(long accountId);
    Account findByAccountNumber(String accountNumber);
    List<Account> getByAccountNumber(String accountNumber);

    Optional<Account> findByIdAndCustomer(Long accountNumber,Customer customer);
}
