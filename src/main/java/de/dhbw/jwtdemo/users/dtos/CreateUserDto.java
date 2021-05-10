package de.dhbw.jwtdemo.users.dtos;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class CreateUserDto {
    @Email(message = "email must be valid email address")
    @NotEmpty(message = "email must be given")
    private String email;

    @Size(min = 6, max = 100, message = "password should minimum have 6 character and maximum 100 characters")
    @NotEmpty(message = "password must be given")
    private String password;

    private Boolean isAdmin = false;
}
