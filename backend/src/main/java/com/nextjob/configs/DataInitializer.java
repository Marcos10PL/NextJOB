package com.nextjob.configs;

import com.nextjob.entities.*;
import com.nextjob.repositories.*;
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
    @Autowired
    private IndustryRepository industryRepository;
    @Autowired
    private ContractTypeRepository contractTypeRepo;
    @Autowired
    private WorkloadTypeRepository workloadTypeRepo;
    @Autowired
    private WorkModeRepository workModeRepo;
    @Autowired
    private PaymentTypeRepository paymentTypeRepo;


    @Override
    public void run(String... args) {
        if (roleRepository.count() == 0) {

            // ROLES
            Role userRole = roleRepository.save(new Role("USER"));
            Role adminRole = roleRepository.save(new Role("ADMIN"));

            // USERS (FOR TESTING)
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

            // INDUSTRIES
            if (industryRepository.count() == 0) {
                industryRepository.save(new Industry("IT"));
                industryRepository.save(new Industry("Education"));
                industryRepository.save(new Industry("Finance"));
                industryRepository.save(new Industry("Retail"));
                industryRepository.save(new Industry("Construction"));
                industryRepository.save(new Industry("Healthcare"));
                industryRepository.save(new Industry("Marketing"));
            }

            // conract_types
            if (contractTypeRepo.count() == 0) {
                contractTypeRepo.save(new ContractType("b2b"));
                contractTypeRepo.save(new ContractType("employment_contract"));
                contractTypeRepo.save(new ContractType("mandate_contract"));
                contractTypeRepo.save(new ContractType("specific_task_contract"));
                contractTypeRepo.save(new ContractType("internship_contract"));
                contractTypeRepo.save(new ContractType("other"));
            }

            if (workloadTypeRepo.count() == 0) {
                workloadTypeRepo.save(new WorkloadType("full_time"));
                workloadTypeRepo.save(new WorkloadType("part_time"));
                workloadTypeRepo.save(new WorkloadType("temporary"));
                workloadTypeRepo.save(new WorkloadType("internship"));
            }

            if (workModeRepo.count() == 0) {
                workModeRepo.save(new WorkMode("remote"));
                workModeRepo.save(new WorkMode("on_site"));
                workModeRepo.save(new WorkMode("hybrid"));
            }

            if (paymentTypeRepo.count() == 0) {
                paymentTypeRepo.save(new PaymentType("hourly"));
                paymentTypeRepo.save(new PaymentType("weekly"));
                paymentTypeRepo.save(new PaymentType("monthly"));
            }
        }

    }
}
