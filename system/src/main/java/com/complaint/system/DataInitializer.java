package com.complaint.system;

import com.complaint.system.enums.Role;
import com.complaint.system.model.User;
import com.complaint.system.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {

        // Create default Admin if not exists
        if (userRepository.findByEmail("admin@system.com").isEmpty()) {
            User admin = User.builder()
                    .name("Admin")
                    .email("admin@system.com")
                    .password(passwordEncoder.encode("Admin@123"))
                    .role(Role.ADMIN)
                    .build();
            userRepository.save(admin);
            System.out.println("==> Default Admin created");
        } else {
            System.out.println("==> Admin already exists");
        }

        // Create default Staff if not exists
        if (userRepository.findByEmail("staff@system.com").isEmpty()) {
            User staff = User.builder()
                    .name("Support Staff")
                    .email("staff@system.com")
                    .password(passwordEncoder.encode("Staff@123"))
                    .role(Role.STAFF)
                    .build();
            userRepository.save(staff);
            System.out.println("==> Default Staff created");
        } else {
            System.out.println("==> Staff already exists");
        }
    }
}
