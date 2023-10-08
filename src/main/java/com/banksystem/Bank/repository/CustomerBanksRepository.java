//package com.banksystem.Bank.repository;
//
//import com.banksystem.Bank.entity.Bank;
//import com.banksystem.Bank.entity.CustomerBanks;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//@Repository
//public interface CustomerBanksRepository extends JpaRepository <CustomerBanks,Long>, JpaSpecificationExecutor<CustomerBanks> {
//    List<CustomerBanks> findAllByBankIdAndCustomerId(Long bankId,Long customerId);
//
//}
