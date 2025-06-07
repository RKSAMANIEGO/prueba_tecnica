package com.prueba.tecnica.nttdata.gestion_employee.service;

import javax.security.auth.login.CredentialException;

import com.prueba.tecnica.nttdata.gestion_employee.model.dtos.LoginRequestDto;
import com.prueba.tecnica.nttdata.gestion_employee.model.dtos.LoginResponseDto;

public interface IAuthService {
 
    LoginResponseDto login (LoginRequestDto request) throws CredentialException ;

}
