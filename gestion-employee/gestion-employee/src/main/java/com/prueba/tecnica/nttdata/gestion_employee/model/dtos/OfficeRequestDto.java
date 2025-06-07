package com.prueba.tecnica.nttdata.gestion_employee.model.dtos;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public record OfficeRequestDto(
    @NotBlank(message = "El nombre no puede estar vacio")
    @Size(min = 3, max=100, message = "El nombre debe tener minimo 3 y maximo 100 caracteres")
    String name
) {}
