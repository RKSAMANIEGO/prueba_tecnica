package com.prueba.tecnica.nttdata.gestion_employee.model.dtos;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.util.Set;


public record EmployeeResponseDto(
    Integer id,
    String name,
    String phone,
    String dni,
    String direction,
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate birthday,
    Set<OfficeResponseDto> offices
) {}
