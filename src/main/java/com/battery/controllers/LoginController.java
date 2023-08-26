package com.battery.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import com.battery.models.LoginRequest;
import com.battery.models.LoginResponse;
import com.battery.security.JwtHelper;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "Login API")
public class LoginController {
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private JwtHelper jwtHelper;

    @PostMapping("/login")
    @Operation(summary = "JWT Token API")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        this.doAuthenticate(request.getUsername(), request.getPassword());
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        String token = jwtHelper.generateToken(userDetails);

        LoginResponse loginResponse = new LoginResponse(request.getUsername(), token);
        return ResponseEntity.ok(loginResponse);
    }

    private void doAuthenticate(String username, String password) {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, password);
        try {
            manager.authenticate(authentication);
        } catch (BadCredentialsException e) {
            throw new RuntimeException("Invalid username or password");
        }
    }
}