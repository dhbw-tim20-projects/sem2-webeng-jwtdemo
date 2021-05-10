package de.dhbw.jwtdemo.users.dtos;

import lombok.Data;

@Data
public class UserDto {
    private String id;
    private String email;
    private Boolean isAdmin;
}
