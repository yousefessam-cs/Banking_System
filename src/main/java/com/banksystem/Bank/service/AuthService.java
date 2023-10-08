package com.banksystem.Bank.service;

import com.banksystem.Bank.dto.CustomerResponseDTO;
import com.banksystem.Bank.dto.LoginDto;
import com.banksystem.Bank.dto.RegisterDto;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface AuthService {
    String login(LoginDto loginDto);

    String register(RegisterDto registerDto);
    boolean isTokenAuthenticated(String token);

    CustomerResponseDTO getUserDetails(int userId);
}
