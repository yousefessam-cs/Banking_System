package com.banksystem.Bank.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "\"Transaction\"")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"Id\"")
    private long id;

    @Column(name = "\"Code\"")
    private String code ;
    @Column(name = "\"CreationDate\"")
    private LocalDate creationDate;
    @Column(name = "\"CreationDateTime\"")
    private LocalDateTime creationDateTime;

    @ManyToOne
    @JoinColumn(name = "\"SenderAccountId\"", referencedColumnName = "\"Id\"")
    private Account senderAccount;

    @ManyToOne
    @JoinColumn(name = "\"ReceiverAccountId\"", referencedColumnName = "\"Id\"")
    private Account receiverAccount;

    @Column(name = "\"Amount\"")
    private BigDecimal amount;


}

