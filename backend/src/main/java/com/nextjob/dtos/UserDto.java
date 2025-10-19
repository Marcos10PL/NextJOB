package com.nextjob.dtos;

import java.util.Date;

public record UserDto(
        Integer id,
        String fullName,
        String email,
        String role,
        Date createdAt,
        Date updatedAt
) {}

