package com.nextjob.services;

import com.nextjob.dtos.UpdateUserDto;
import com.nextjob.entities.User;
import com.nextjob.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> allUsers() {
        return userRepository.findAllUsers();
    }

    @Transactional
    public void deleteUser(int id) {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException("User with id " + id + " not found");
        }
        userRepository.deleteById(id);
    }

    @Transactional
    public User updateUser(int userId, UpdateUserDto dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        if (dto.fullName() != null) {
            user.setFullName(dto.fullName());
        }

        if (dto.email() != null) {
            userRepository.findByEmail(dto.email())
                    .filter(u -> !u.getId().equals(userId))
                    .ifPresent(u -> {
                        throw new IllegalArgumentException("Email already in use");
                    });

            user.setEmail(dto.email());
        }

        // address info (directly in user)
        if (dto.address() != null) {
            user.setAddress(dto.address());
        }

        if (dto.city() != null) {
            user.setCity(dto.city());
        }

        if (dto.country() != null) {
            user.setCountry(dto.country());
        }

        return userRepository.save(user);
    }
}
