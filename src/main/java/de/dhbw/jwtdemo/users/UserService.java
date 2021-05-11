package de.dhbw.jwtdemo.users;

import de.dhbw.jwtdemo.users.dtos.CreateUserDto;
import de.dhbw.jwtdemo.users.dtos.UserDto;
import de.dhbw.jwtdemo.users.dtos.UserMapper;
import de.mkammerer.snowflakeid.SnowflakeIdGenerator;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDto create(final CreateUserDto userDto) {
        var user = new User();
        user.setId(String.valueOf(SnowflakeIdGenerator.createDefault(0).next()));
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setIsAdmin(userDto.getIsAdmin());
        this.userRepository.save(user);
        return UserMapper.toDto(user);
    }

    public boolean isEmailUnique(final String email) {
        return this.userRepository.countByEmail(email) == 0;
    }

    public Optional<User> findByEmail(final String email) {
        return this.userRepository.findByEmail(email);
    }

    public List<User> findAll() {
        return this.userRepository.findAll();
    }
}
