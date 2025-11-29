package com.firma.gestiune_it.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        // aici permitem accesul la fisiere statice (css, js, imagini) fara login
                        .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
                        // orice alta cerere necesita autentificare (login)
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        // vom folosi pagina de login default a Spring
                        // Cand vom face frontend-ul, vom personaliza aici
                        .permitAll()
                        .defaultSuccessUrl("/", true) // Daca login-ul e ok, mergem pe prima pagina
                )
                .logout((logout) -> logout
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                );

        return http.build();
    }

    // aici definim algoritmul de criptare a parolelor
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}