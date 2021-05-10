package de.dhbw.jwtdemo.users;

import de.dhbw.jwtdemo.core.exceptions.HttpException;
import de.dhbw.jwtdemo.users.dtos.CreateUserDto;
import de.dhbw.jwtdemo.users.dtos.UserDto;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "users")
@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Create a new user")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "User created"),
            @ApiResponse(responseCode = "409", description = "User with given email already exists"),
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto create(@Valid @RequestBody final CreateUserDto userDto) {
        if (!this.userService.isEmailUnique(userDto.getEmail())) {
            throw new HttpException(HttpStatus.CONFLICT, "user with the given email already exists");
        }
        return this.userService.create(userDto);
    }
}
