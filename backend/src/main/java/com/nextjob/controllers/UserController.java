package com.nextjob.controllers;

import com.nextjob.dtos.LoginUserDto;
import com.nextjob.dtos.UserDto;
import com.nextjob.entities.User;
import com.nextjob.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/users")
@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User currentUser = (User) authentication.getPrincipal();

        UserDto dto = new UserDto(
                currentUser.getId(),
                currentUser.getFullName(),
                currentUser.getEmail(),
                currentUser.getRole().getName(),
                currentUser.getCreatedAt(),
                currentUser.getUpdatedAt()
        );

        return ResponseEntity.ok(dto);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserDto>> allUsers() {
        List<UserDto> userDtos = userService.allUsers().stream()
                .map(u -> new UserDto(
                        u.getId(),
                        u.getFullName(),
                        u.getEmail(),
                        u.getRole().getName(),
                        u.getCreatedAt(),
                        u.getUpdatedAt()
                ))
                .toList();

        return ResponseEntity.ok(userDtos);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build(); // HTTP 204
    }

}
