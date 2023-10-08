//package com.banksystem.Bank.entity;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import java.util.List;
//
//@Setter
//@Getter
//@NoArgsConstructor
//@AllArgsConstructor
//@Entity
//@Table(name = "\"CustomerBanks\"")
//public class CustomerBanks {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "\"Id\"")
//    private Long Id;
////    //@JoinColumn(name = "\"Customer\"", referencedColumnName = "\"id\"")
////    @Column(name = "\"CustomerId\"")
////    private Long customerId;
////    //@JoinColumn(name = "\"Bank\"", referencedColumnName = "\"id\"")
////    @Column(name = "\"BankId\"")
////    private Long bankId;
//
//    @ManyToOne
//    @JoinColumn(name = "\"CustomerId\"")
//    private Customer customer;
//
//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "\"BankId\"", referencedColumnName = "\"Id\"")
//    private Bank bank;
//}
