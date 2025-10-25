package com.nextjob.mappers;

import com.nextjob.dtos.UserDto;
import com.nextjob.entities.User;

public class UserMapper {
    public static UserDto toDto(User user) {
        return new UserDto(
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                user.getRole().getName(),
                user.getCreatedAt(),
                user.getUpdatedAt(),
                user.getAddress(),
                user.getCity(),
                user.getCountry()
        );
    }
}
