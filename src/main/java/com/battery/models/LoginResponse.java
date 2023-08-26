package com.battery.models;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {
    private String username;
    private String token;

    public LoginResponse(String username, String token){
        this.username = username;
        this.token = token;
    }
}
