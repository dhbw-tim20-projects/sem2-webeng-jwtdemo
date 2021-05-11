package de.dhbw.jwtdemo.auth.dto;

import lombok.Data;

import java.util.Date;

@Data
public class LoginResponse {
    private String accessToken;
    private Date expiresAt;
}
