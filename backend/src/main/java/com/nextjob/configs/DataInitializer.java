package com.nextjob.configs;

import com.nextjob.entities.Role;
import com.nextjob.entities.User;
import com.nextjob.repositories.RoleRepository;
import com.nextjob.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (roleRepository.count() == 0) {
            Role userRole = roleRepository.save(new Role("USER"));
            Role adminRole = roleRepository.save(new Role("ADMIN"));

            if (userRepository.count() == 0) {
                User user = new User();
                user.setFullName("Jan Kowalski");
                user.setEmail("user@example.com");
                user.setPassword(passwordEncoder.encode("user1234"));
                user.setRole(userRole);
                userRepository.save(user);

                User admin = new User();
                admin.setFullName("Admin Admin");
                admin.setEmail("admin@example.com");
                admin.setPassword(passwordEncoder.encode("admin1234"));
                admin.setRole(adminRole);
                userRepository.save(admin);
            }
        }

    }
}
