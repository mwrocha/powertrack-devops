package br.com.powertrack.config;

import br.com.powertrack.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Bean
    public UserDetailsService userDetailsService(UserRepository repo) {
        return username -> {
            System.out.println("Buscando usuário: " + username);
            try {
                var user = repo.findByUsername(username);
                System.out.println("Resultado: " + user);
                return user.map(u -> {
                    System.out.println("Role: " + u.getRole());
                    return User.withUsername(u.getUsername())
                            .password(u.getPassword())
                            .authorities("ROLE_" + u.getRole())
                            .build();
                }).orElseThrow(() -> new RuntimeException("User not found: " + username));
            } catch (Exception e) {
                System.out.println("ERRO: " + e.getMessage());
                e.printStackTrace();
                throw e;
            }
        };
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance(); // só para teste acadêmico
    }
}