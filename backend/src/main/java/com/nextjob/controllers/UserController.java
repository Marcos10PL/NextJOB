package com.nextjob.controllers;

import com.nextjob.dtos.UpdateUserDto;
import com.nextjob.dtos.UserDto;
import com.nextjob.entities.User;
import com.nextjob.mappers.UserMapper;
import com.nextjob.services.UserService;
import jakarta.validation.Valid;
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
        return ResponseEntity.ok(UserMapper.toDto(currentUser));
    }

    @PatchMapping("/me")
    public ResponseEntity<UserDto> updateOwnProfile(@Valid @RequestBody UpdateUserDto dto) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User updatedUser = userService.updateUser(currentUser.getId(), dto);
        return ResponseEntity.ok(UserMapper.toDto(updatedUser));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserDto>> allUsers() {
        List<UserDto> userDtos = userService.allUsers().stream()
                .map(UserMapper::toDto)
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
