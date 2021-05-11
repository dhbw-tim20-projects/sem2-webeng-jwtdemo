package de.dhbw.jwtdemo.users;

import de.dhbw.jwtdemo.core.exceptions.HttpException;
import de.dhbw.jwtdemo.users.dtos.CreateUserDto;
import de.dhbw.jwtdemo.users.dtos.UserDto;
import de.dhbw.jwtdemo.users.dtos.UserMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto create(@Valid @RequestBody final CreateUserDto userDto) {
        if (!this.userService.isEmailUnique(userDto.getEmail())) {
            throw new HttpException(HttpStatus.CONFLICT, "user with the given email already exists");
        }
        return this.userService.create(userDto);
    }

    @GetMapping("current")
    public UserDto findById(Principal principal) {
        var user = userService.findByEmail(principal.getName());
        if (user.isEmpty()) {
            throw new HttpException(HttpStatus.NOT_FOUND, "user not found");
        }
        return UserMapper.toDto(user.get());
    }

    @GetMapping
    public List<UserDto> listAll(Principal principal) {
        var user = userService.findByEmail(principal.getName());
        if(user.isEmpty()) {
            throw new HttpException(HttpStatus.NOT_FOUND, "user not found");
        }

        if(!user.get().getIsAdmin()) {
            throw new HttpException(HttpStatus.UNAUTHORIZED, "user is not permitted to list all users");
        }

        return this.userService.findAll().stream().map(UserMapper::toDto).collect(Collectors.toList());
    }
}
