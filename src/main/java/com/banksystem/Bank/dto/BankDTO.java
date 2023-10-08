package com.banksystem.Bank.dto;

import lombok.Data;

import java.util.List;

@Data
public class BankDTO {
    private long id;
    private String bankName;
    private List<AccountDTO> accounts;
}
