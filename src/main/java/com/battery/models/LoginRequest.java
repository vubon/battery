package com.battery.models;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema
public class LoginRequest {
    @Schema(defaultValue = "vubon")
    @NotBlank(message = "Username can't be blank")
    private String username;

    @Schema(defaultValue = "password")
    @NotBlank(message = "Password can't be blank")
    private String password;
}
