package com.banksystem.Bank.controller;

import com.banksystem.Bank.dto.CustomerResponseDTO;
import com.banksystem.Bank.dto.JWTAuthResponse;
import com.banksystem.Bank.dto.LoginDto;
import com.banksystem.Bank.dto.RegisterDto;
import com.banksystem.Bank.entity.Account;
import com.banksystem.Bank.security.JwtTokenProvider;
import com.banksystem.Bank.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;
    private JwtTokenProvider jwtTokenProvider;

    public AuthController(AuthService authService,JwtTokenProvider jwtTokenProvider) {
        this.authService = authService;
        this.jwtTokenProvider=jwtTokenProvider;
    }

    // Build Login REST API
    @PostMapping(value = {"/login", "/signin"})
    public ResponseEntity<JWTAuthResponse> login(@RequestBody LoginDto loginDto){
        String token = authService.login(loginDto);

        JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();
        jwtAuthResponse.setAccessToken(token);

        return ResponseEntity.ok(jwtAuthResponse);
    }

    // Build Register REST API
    @PostMapping(value = {"/register/"})
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto){
        String response = authService.register(registerDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @GetMapping("/get-userDetails")
    public ResponseEntity<?> getUserDetails(@RequestHeader(name = "Authorization") String token, Authentication authentication){
        String name = authentication.getName();
        int userId = jwtTokenProvider.extractUserId(token.substring(7));
        CustomerResponseDTO customerResponseDTO=authService.getUserDetails(userId);
        return new ResponseEntity<>(customerResponseDTO, HttpStatus.CREATED);
    }


}
