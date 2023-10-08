package com.banksystem.Bank.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.boot.autoconfigure.cassandra.CassandraConnectionDetails;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "\"Customers\"")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"Id\"")
    private Long id;
    @Column(name = "\"FirstName\"")
    private String firstName;
    @Column(name = "\"LastName\"")
    private String lastName;
    @Column(name = "\"UserName\"")
    private String username;
    @Column(name = "\"Email\"")
    private String email;
    @Column(name = "\"Password\"")
    private String password;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Account> accounts;

    @ManyToOne
    @JoinColumn(name = "\"BankId\"")
    private Bank bank;


//    @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    private List<CustomerBanks> customerBanks;



    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "\"customers_roles\"",
            joinColumns = @JoinColumn(name = "\"CustomerId\"", referencedColumnName = "\"Id\""),
            inverseJoinColumns = @JoinColumn(name = "\"RoleId\"", referencedColumnName = "\"Id\"")
    )
    private Set<Role> roles;
//    @OneToMany
//    private List<Account>accounts;

}
