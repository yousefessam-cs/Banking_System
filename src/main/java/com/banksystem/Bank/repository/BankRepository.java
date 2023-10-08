package com.banksystem.Bank.repository;

import com.banksystem.Bank.entity.Bank;
import com.banksystem.Bank.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface BankRepository extends JpaRepository<Bank,Long> {
    @Override
    Optional<Bank> findById(Long bankId);
//    Optional<Bank> findBanksByCustomers( List<Customer> customers);
}
