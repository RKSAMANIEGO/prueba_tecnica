package com.prueba.tecnica.nttdata.gestion_employee.model.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequestDto(

        @Schema(description = "Correo valido example@gmail.com")
        @Email(message = "Ingrese el formato adecuado del correo, example@gmail.com")
        @NotBlank(message = "El correo no puede estar vacio")
        String email,

        @Schema(description = "Contraseña valida Prueba123* ")
        @NotBlank(message = "Ingrese la contraseña no puede estar vacio")
        String password
) {}
