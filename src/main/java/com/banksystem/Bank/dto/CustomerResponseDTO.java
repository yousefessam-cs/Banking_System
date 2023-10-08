package com.banksystem.Bank.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerResponseDTO {
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String bankCode;
    private String bankName;
}
