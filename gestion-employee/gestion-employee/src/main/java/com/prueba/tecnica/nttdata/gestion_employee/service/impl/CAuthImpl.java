package com.prueba.tecnica.nttdata.gestion_employee.service.impl;
import javax.security.auth.login.CredentialException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import com.prueba.tecnica.nttdata.gestion_employee.model.dtos.LoginRequestDto;
import com.prueba.tecnica.nttdata.gestion_employee.model.dtos.LoginResponseDto;
import com.prueba.tecnica.nttdata.gestion_employee.security.JwtUtils;
import com.prueba.tecnica.nttdata.gestion_employee.service.IAuthService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CAuthImpl implements IAuthService {
    
    private final AuthenticationManager authManager;
    private final JwtUtils utils;

    @Override
    public LoginResponseDto login(LoginRequestDto request) throws CredentialException {
        try {
            Authentication auth = authManager.authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));
            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            String token = utils.createToken(userDetails);
            return new LoginResponseDto(userDetails.getUsername(), token);
        } catch (Exception e) {
            throw new CredentialException("Credenciales No Validas");
        }

    }
    
}
