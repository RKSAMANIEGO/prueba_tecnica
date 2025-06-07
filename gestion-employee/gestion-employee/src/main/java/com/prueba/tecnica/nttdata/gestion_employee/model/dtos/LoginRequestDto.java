package com.prueba.tecnica.nttdata.gestion_employee.model.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequestDto(

        @Email(message = "Ingrese el formato adecuado del correo, example@gmail.com")
        @NotBlank(message = "El correo no puede estar vacio")
        String email,

        @NotBlank(message = "Ingrese la contraseña no puede estar vacio")
        String password
) {}
