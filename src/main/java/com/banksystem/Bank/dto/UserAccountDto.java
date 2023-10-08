package com.banksystem.Bank.dto;

import com.banksystem.Bank.entity.AccountType;
import lombok.*;

import java.math.BigDecimal;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserAccountDto {
    private long id;
    private String name;
    private String accountNumber;
    private AccountType type;
    private BigDecimal balance;
}
