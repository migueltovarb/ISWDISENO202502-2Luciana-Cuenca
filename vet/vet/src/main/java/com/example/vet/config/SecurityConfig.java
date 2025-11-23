package com.example.vet.config;

import com.example.vet.security.JwtAuthenticationFilter;
import com.example.vet.security.JwtTokenProvider;
import com.example.vet.services.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
@Slf4j
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(UserService userService) {
        return new JwtAuthenticationFilter(jwtTokenProvider, userService);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000", "http://localhost:4200", "http://127.0.0.1:3000"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, UserService userService) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // No incluir el context-path (/api) en los matchers
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/v3/api-docs/**").permitAll()
                        .requestMatchers("/swagger-ui/**").permitAll()
                        .requestMatchers("/swagger-ui.html").permitAll()
                        .requestMatchers(HttpMethod.GET, "/users/**").hasAnyRole("ADMIN", "VETERINARIAN", "RECEPTIONIST")
                        .requestMatchers(HttpMethod.POST, "/users/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/users/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/users/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/owners/**").hasAnyRole("ADMIN", "VETERINARIAN", "RECEPTIONIST")
                        .requestMatchers(HttpMethod.POST, "/owners/**").hasAnyRole("ADMIN", "RECEPTIONIST")
                        .requestMatchers(HttpMethod.PUT, "/owners/**").hasAnyRole("ADMIN", "RECEPTIONIST")
                        .requestMatchers(HttpMethod.DELETE, "/owners/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/pets/**").hasAnyRole("ADMIN", "VETERINARIAN", "RECEPTIONIST")
                        .requestMatchers(HttpMethod.POST, "/pets/**").hasAnyRole("ADMIN", "RECEPTIONIST")
                        .requestMatchers(HttpMethod.PUT, "/pets/**").hasAnyRole("ADMIN", "RECEPTIONIST")
                        .requestMatchers(HttpMethod.DELETE, "/pets/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/appointments/**").hasAnyRole("ADMIN", "VETERINARIAN", "RECEPTIONIST")
                        .requestMatchers(HttpMethod.POST, "/appointments/**").hasAnyRole("ADMIN", "RECEPTIONIST")
                        .requestMatchers(HttpMethod.PUT, "/appointments/**").hasAnyRole("ADMIN", "VETERINARIAN", "RECEPTIONIST")
                        .requestMatchers(HttpMethod.DELETE, "/appointments/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/consultations/**").hasAnyRole("ADMIN", "VETERINARIAN")
                        .requestMatchers(HttpMethod.POST, "/consultations/**").hasRole("VETERINARIAN")
                        .requestMatchers(HttpMethod.PUT, "/consultations/**").hasRole("VETERINARIAN")
                        .requestMatchers(HttpMethod.DELETE, "/consultations/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter(userService), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
