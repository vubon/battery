package com.battery.models;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema
public class LoginRequest {
    @Schema(defaultValue = "vubon")
    private String username;
    @Schema(defaultValue = "password")
    private String password;
}
