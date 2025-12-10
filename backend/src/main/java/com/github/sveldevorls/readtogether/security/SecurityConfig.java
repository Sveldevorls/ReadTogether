package com.github.sveldevorls.readtogether.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public RoleHierarchy roleHierarchy() {
        String hierarchy = """
                ROLE_ADMIN > ROLE_MODERATOR
                ROLE_MODERATOR > ROLE_USER
            """;

        return RoleHierarchyImpl.fromHierarchy(hierarchy);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
            .sessionManagement(session -> 
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .authorizeHttpRequests(req -> req
                .requestMatchers("/api/register", "/api/login").permitAll()
                .requestMatchers("/api/users/{username}").permitAll()
                .requestMatchers("/api/test/*").permitAll()
                .requestMatchers("/api/verify").authenticated()
                .anyRequest().hasRole("ADMIN")
            )
            .addFilterBefore(
                jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class
            );
        
        return http.build();
    }
}
