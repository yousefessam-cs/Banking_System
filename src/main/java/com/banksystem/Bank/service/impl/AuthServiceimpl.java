package com.banksystem.Bank.service.impl;

import com.banksystem.Bank.dto.CustomerResponseDTO;
import com.banksystem.Bank.dto.LoginDto;
import com.banksystem.Bank.dto.RegisterDto;
import com.banksystem.Bank.dto.securityUser;
import com.banksystem.Bank.entity.Bank;
import com.banksystem.Bank.entity.Customer;
import com.banksystem.Bank.entity.Role;
import com.banksystem.Bank.exciption.BankApiException;
import com.banksystem.Bank.repository.BankRepository;
import com.banksystem.Bank.repository.CustomerRepository;
import com.banksystem.Bank.repository.RoleRepository;
import com.banksystem.Bank.security.JwtTokenProvider;
import com.banksystem.Bank.service.AuthService;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AuthServiceimpl implements AuthService {
    private AuthenticationManager authenticationManager;
    private CustomerRepository customerRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private JwtTokenProvider jwtTokenProvider;

    private BankRepository bankRepository;
    @Value("${app.jwt-secret}")
    private String SECRET_KEY;


    public AuthServiceimpl(AuthenticationManager authenticationManager, CustomerRepository customerRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider, BankRepository bankRepository) {
        this.authenticationManager = authenticationManager;
        this.customerRepository = customerRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.bankRepository = bankRepository;
    }

    @Override
    public String login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String username=authentication.getName();
        Customer user1 = customerRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("User not found"));
//        List<Customer> customers=new ArrayList<>();
//        customers.add(user1);
        Bank bank=user1.getBank();
//        Bank bank=bankRepository.findBanksByCustomers(customers).orElseThrow(() -> new IllegalArgumentException("User not found"));
//        List<CustomerBanks> customerBanks = user1.getCustomerBanks();
//        List<Bank> bankList = new ArrayList<>();
//        for (CustomerBanks customerBank : customerBanks){
//            bankList.add(customerBank.getBank());
//        }
        securityUser savedUser = securityUser.builder()
                .id(user1.getId())
                .bank(bank)
                .roles(user1.getRoles())
                .username(loginDto.getUsernameOrEmail())
                .build();

        String token = jwtTokenProvider.generateToken(savedUser);

        return token;
    }

    @Override
    public String register(RegisterDto registerDto) {

        if(customerRepository.existsByUsername(registerDto.getUsername())){
            throw new BankApiException(HttpStatus.BAD_REQUEST, "Username is already exists!.");
        }

        // add check for email exists in database
        if(customerRepository.existsByEmail(registerDto.getEmail())){
            throw new BankApiException(HttpStatus.BAD_REQUEST, "Email is already exists!.");
        }
        Bank bank = bankRepository.findById(registerDto.getBankId()).orElseThrow(()-> new IllegalArgumentException("Bank not found"));

        Customer user = new Customer();
        user.setFirstName(registerDto.getFirstName());
        user.setLastName(registerDto.getLastName());
        user.setUsername(registerDto.getUsername());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

//        List<CustomerBanks> customerBanks= new ArrayList<>();
//        customerBanks.add(new CustomerBanks(null, user, bank));
        user.setBank(bank);

        Set<Role> roles = new HashSet<>();
        Role userRole =roleRepository.findByName("ROLE_USER").isPresent()?roleRepository.findByName("ROLE_USER").get():null;
        roles.add(userRole);
        user.setRoles(roles);

        customerRepository.save(user);

        return "User registered successfully!.";
    }
    @Override
    public boolean isTokenAuthenticated(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            //System.out.println("Error");
            return false;
        }
    }

    @Override
    public CustomerResponseDTO getUserDetails(int userId) {
        Customer user = customerRepository.findById((long) userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        CustomerResponseDTO customerResponseDTO=new CustomerResponseDTO();
        customerResponseDTO.setFirstName(user.getFirstName());
        customerResponseDTO.setLastName(user.getLastName());
        customerResponseDTO.setUsername(user.getUsername());
        customerResponseDTO.setEmail(user.getEmail());
        customerResponseDTO.setBankName(user.getBank().getName());
        customerResponseDTO.setBankCode(user.getBank().getCode());
        return customerResponseDTO;
    }
}
