package com.banksystem.Bank.dto;

import com.banksystem.Bank.entity.AccountType;
import com.banksystem.Bank.entity.Bank;
import com.banksystem.Bank.entity.Customer;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO {
    private Long id;
    private String accountNumber;
    @Enumerated(EnumType.STRING)
    private AccountType type;
    private String name;
    private Long bankId;
    private Customer customer;
    private BigDecimal balance;
}
