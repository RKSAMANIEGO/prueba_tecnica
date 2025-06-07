package com.prueba.tecnica.nttdata.gestion_employee.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.prueba.tecnica.nttdata.gestion_employee.security.FilterAuthentication;
import com.prueba.tecnica.nttdata.gestion_employee.service.impl.CUserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CUserDetailsServiceImpl userDetailService;
    private final FilterAuthentication filterAuth;
    @Bean
    SecurityFilterChain chain(HttpSecurity http) throws Exception{
        return http
                .csrf(csrf->csrf.disable())
                .sessionManagement(session-> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize ->authorize
                            .requestMatchers("/api/login").permitAll()
                            .requestMatchers(HttpMethod.DELETE,"/api/employee/{id}").hasRole("ADMIN")
                            .anyRequest().authenticated()
                )
                .addFilterBefore(filterAuth, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    AuthenticationManager auth (AuthenticationConfiguration config) throws Exception{
        return config.getAuthenticationManager();
    }

    @Bean
    AuthenticationProvider provider(){
      DaoAuthenticationProvider dao = new DaoAuthenticationProvider();
      dao.setPasswordEncoder(passwordEncoder());
      dao.setUserDetailsService(userDetailService);;
      return dao;
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    
}
