package com.prueba.tecnica.nttdata.gestion_employee.model.dtos;

import java.time.LocalDate;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

public record EmployeeRequestDto(

    @NotBlank(message = "El nombre no puede estar vacio")
    @Size(min = 2, max =80,message = "El nombre debe tener minimo 2 y maximo 80 caracteres")
    String name,
    
    @Size(min = 9, max =11,message = "El celular debe tener minimo 9 caracteres")
    String phone,

    @NotBlank(message = "El dni no puede estar vacio")
    @Size(min = 8, message = "El dni debe tener minimo 8 caracteres")
    String dni,

    @Size(max = 100, message = "La direccion debe tener maximo 100 caracteres")
    String direction,

    @NotNull(message = "La fecha de nacimiento es obligatoria")
    @Past(message = "La fecha tiene que ser del pasado")
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate birthday,

    Set<Integer> offices

) {}
