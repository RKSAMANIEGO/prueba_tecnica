package com.prueba.tecnica.nttdata.gestion_employee.controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.prueba.tecnica.nttdata.gestion_employee.model.dtos.LoginRequestDto;
import com.prueba.tecnica.nttdata.gestion_employee.model.dtos.LoginResponseDto;
import com.prueba.tecnica.nttdata.gestion_employee.service.IAuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import javax.security.auth.login.CredentialException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "Autenticación", description = "Acceso al sistema autenticacion y autorización")
public class AuthController {

    private final IAuthService service;
    

    //login
    @Operation(summary = "Ingreso al sistema con roles como ADMIN o GERENTE",    description = """
        Autenticación de usuarios registrados. Según el rol:
        - **ADMIN (enrike@gmail.com / enrike123)**: puede realizar operaciones CRUD completas.
        - **GERENTE (stefano@gmail.com / stefano123)**: puede listar, crear y actualizar.
        """)
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200" , description = "Autenticacion Existoso",content = @Content(schema = @Schema(implementation = LoginResponseDto.class))),
            @ApiResponse(responseCode = "401", description = "Credenciales inválidas", content = @Content(schema = @Schema(example = "Credenciales Incorrectas")))
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody( required = true,description = "Credenciales de acceso",
    content = @Content( mediaType = "application/json",schema = @Schema(implementation = LoginRequestDto.class, example = """
            {
              "email": "enrike@gmail.com",
              "password": "123"
            }
        """) ) )
        
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody @Valid LoginRequestDto request)  throws CredentialException  { 
        return new ResponseEntity<LoginResponseDto>(service.login(request), HttpStatus.OK);
    }
    
}
