package br.pucpr.lanchonete.config;

import br.pucpr.lanchonete.model.Role;
import br.pucpr.lanchonete.model.User;
import br.pucpr.lanchonete.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class AdminUserInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        userRepository.findByEmail("admin@lanchonete.com")
                .ifPresentOrElse(
                        user -> {
                           
                        },
                        () -> {
                            User admin = new User();
                            admin.setNome("Administrador");
                            admin.setEmail("admin@lanchonete.com");
                            admin.setSenha(passwordEncoder.encode("admin123"));
                            admin.setRoles(Set.of(Role.ROLE_ADMIN));
                            userRepository.save(admin);
                        }
                );
    }
}

