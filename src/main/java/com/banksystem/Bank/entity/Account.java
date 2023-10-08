package com.banksystem.Bank.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.internal.util.stereotypes.Lazy;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "\"Account\"")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"Id\"")
    private Long id;

    @NotNull
    @Column(name = "\"AccountNumber\"",unique=true)
    private String accountNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "\"Type\"")
    private AccountType type;
    @Column(name = "\"Name\"")
    private String name;

    @ManyToOne
    @JoinColumn(name = "\"BankId\"")
    private Bank bank;

    @ManyToOne
    @JoinColumn(name = "\"CustomerId\"")
    private Customer customer;
    @NotNull
    @Column(name = "\"Balance\"")
    private BigDecimal balance;

    @OneToMany(mappedBy = "senderAccount",cascade = CascadeType.ALL)
    private List<Transaction> SentTransactions;
    @OneToMany(mappedBy = "receiverAccount",cascade = CascadeType.ALL)
    private List<Transaction> ReceivedTransactions;


}
