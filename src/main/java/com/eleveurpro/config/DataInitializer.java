package com.eleveurpro.config;

import com.eleveurpro.entity.User;
import com.eleveurpro.entity.enums.Role;
import com.eleveurpro.repository.UserRepository;
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
    public void run(String... args) {
        if (!userRepository.existsByEmail("admin@eleveurpro.com")) {
            User admin = User.builder()
                    .nom("Admin")
                    .prenom("Super")
                    .email("admin@eleveurpro.com")
                    .telephone("+221700000000")
                    .password(passwordEncoder.encode("admin123"))
                    .role(Role.ADMIN)
                    .actif(true)
                    .build();
            userRepository.save(admin);
        }

        if (!userRepository.existsByEmail("eleveur@eleveurpro.com")) {
            User eleveur = User.builder()
                    .nom("Diop")
                    .prenom("Mamadou")
                    .email("eleveur@eleveurpro.com")
                    .telephone("+221770000000")
                    .password(passwordEncoder.encode("eleveur123"))
                    .role(Role.ELEVEUR)
                    .actif(true)
                    .build();
            userRepository.save(eleveur);
        }

        if (!userRepository.existsByEmail("veto@eleveurpro.com")) {
            User veto = User.builder()
                    .nom("Fall")
                    .prenom("Aminata")
                    .email("veto@eleveurpro.com")
                    .telephone("+221780000000")
                    .password(passwordEncoder.encode("veto123"))
                    .role(Role.VETERINAIRE)
                    .actif(true)
                    .build();
            userRepository.save(veto);
        }
    }
}
