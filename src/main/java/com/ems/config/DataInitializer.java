package com.ems.config;

import com.ems.model.AdminUser;
import com.ems.model.Department;
import com.ems.repository.AdminUserRepository;
import com.ems.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner seedData(AdminUserRepository adminRepo,
                               DepartmentRepository deptRepo,
                               PasswordEncoder encoder,
                               @Value("${app.admin.username}") String username,
                               @Value("${app.admin.password}") String password) {
        return args -> {
            if (adminRepo.findByUsername(username).isEmpty()) {
                adminRepo.save(new AdminUser(username, encoder.encode(password)));
            }
            if (deptRepo.count() == 0) {
                deptRepo.saveAll(List.of(
                        new Department("Engineering"),
                        new Department("Human Resources"),
                        new Department("Finance"),
                        new Department("Marketing"),
                        new Department("Sales")));
            }
        };
    }
}
