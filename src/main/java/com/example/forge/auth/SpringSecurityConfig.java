package com.example.forge.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.observation.ObservationProperties.Http;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.forge.auth.filters.JwtAuthenticationFilter;
import com.example.forge.auth.filters.JwtValidationFilter;

@Configuration
public class SpringSecurityConfig {
  @Autowired
  private AuthenticationConfiguration authenticationConfiguration;
  @Bean
  PasswordEncoder passwordEncoder(){
    return new BCryptPasswordEncoder();
  };
  @Bean
  AuthenticationManager authenticationManager() throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }
  @Bean
  SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    return http.authorizeHttpRequests(authRules -> authRules
      .requestMatchers(HttpMethod.GET, "/api/users").permitAll()
      .requestMatchers(HttpMethod.GET, "/api/users/{id}").hasAnyRole("USER", "ADMIN")
      .requestMatchers("/api/users/**").hasRole("ADMIN")
      // .requestMatchers(HttpMethod.POST, "/api/users").hasRole("ADMIN")
      // .requestMatchers(HttpMethod.DELETE, "api/users/{id}").hasRole("ADMIN")
      // .requestMatchers(HttpMethod.PUT, "api/users/{id}").hasRole("ADMIN")
      .anyRequest().authenticated())
      .addFilter(new JwtAuthenticationFilter(authenticationConfiguration.getAuthenticationManager()))
      .addFilter(new JwtValidationFilter(authenticationConfiguration.getAuthenticationManager()))
      .csrf(config -> config.disable())
      .sessionManagement(managment -> managment.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
      .build();
    }
}
