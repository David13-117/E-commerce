package com.ecomerce.ecommerce.config;

import com.ecomerce.ecommerce.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
public class SecurityConfig {
    
    private final JwtAuthenticationFilter jwtAutenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAutenticationFilter) {
        this.jwtAutenticationFilter = jwtAutenticationFilter;
    }
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception{
        http.csrf(csrf->csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll()//permitir acceso a auth sin token
                        .anyRequest().authenticated()//proteger todas las demas rutas   
                )
                
                .addFilterBefore(jwtAutenticationFilter, UsernamePasswordAuthenticationFilter.class);
                return http.build();
    }
    
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public AuthenticationConfiguration authenticationConfiguration(){
        return new AuthenticationConfiguration();
    }
    
}
