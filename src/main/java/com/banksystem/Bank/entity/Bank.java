package com.banksystem.Bank.entity;
import com.banksystem.Bank.entity.Customer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@Entity
@Table(name = "\"Banks\"")
public class Bank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"Id\"")
    private Long id;
    @Column(name = "\"Name\"")
    private String name;
    @Column(name = "\"Code\"")
    private String code;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "bank")
    private List<Account> accounts;
    @OneToMany(mappedBy = "bank",cascade = CascadeType.ALL)
    private List<Customer> customers;


//
//    @OneToMany(mappedBy = "bank", fetch = FetchType.EAGER)
//    private List<CustomerBanks> customerBanks;



}
