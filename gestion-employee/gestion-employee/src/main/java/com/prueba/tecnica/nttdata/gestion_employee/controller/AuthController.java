package com.prueba.tecnica.nttdata.gestion_employee.controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.prueba.tecnica.nttdata.gestion_employee.model.dtos.LoginRequestDto;
import com.prueba.tecnica.nttdata.gestion_employee.model.dtos.LoginResponseDto;
import com.prueba.tecnica.nttdata.gestion_employee.security.JwtUtils;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {
    
    private final AuthenticationManager authManager;     
    private final JwtUtils utils;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequestDto request) { 
        try {
            Authentication auth = authManager.authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));
            UserDetails userDetails =(UserDetails) auth.getPrincipal();
            String token =utils.createToken(userDetails);
            System.out.println("user "+userDetails.getUsername() +" token "+token);
            return new ResponseEntity<LoginResponseDto>(new LoginResponseDto(userDetails.getUsername(),token), HttpStatus.OK);
        } catch (Exception e) {
            System.err.println("Error autenticando: " + e.getMessage());
            return new ResponseEntity<Map<String,String>>(Map.of("message","Credenciales No Valida ") , HttpStatus.UNAUTHORIZED);
        }
    }
    
}
