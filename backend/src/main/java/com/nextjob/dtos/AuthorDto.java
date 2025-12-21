package com.nextjob.dtos;

public record AuthorDto(
        Integer id,
        String fullName,
        String email,
        String address,
        String city,
        String country
) {}
