package de.dhbw.jwtdemo.users.dtos;

import de.dhbw.jwtdemo.users.User;

public class UserMapper {
    public static UserDto toDto(final User user) {
        var dto = new UserDto();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setIsAdmin(user.getIsAdmin());
        return dto;
    }
}
