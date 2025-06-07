package com.prueba.tecnica.nttdata.gestion_employee.controller;
import java.util.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.prueba.tecnica.nttdata.gestion_employee.model.dtos.EmployeeRequestDto;
import com.prueba.tecnica.nttdata.gestion_employee.model.dtos.EmployeeResponseDto;
import com.prueba.tecnica.nttdata.gestion_employee.service.IEmployeeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/employee")
@RequiredArgsConstructor
@Tag(name = "Employee" , description = "Gestion de los empleados por oficina")
public class EmployeeController {
    
    private final IEmployeeService service;

    // List all
    @Operation(summary = "Lista de empleados", description = "Lista de empleados con sus oficinas asignadas")
    @ApiResponse(responseCode="200", content = @Content( mediaType = "application/json", array = @ArraySchema( schema = @Schema(implementation = EmployeeResponseDto.class ) )) )
    
    @GetMapping("")
    public ResponseEntity<List<EmployeeResponseDto>> listAll(){
        return new ResponseEntity<List<EmployeeResponseDto>>(service.listAll(), HttpStatus.OK);
    }


    // find by id
    @Operation(summary = "Buscar por Empleado",description = "Buscar los empleados por su ID")
    @ApiResponses(value={
        @ApiResponse(responseCode = "200" ,content = @Content(schema = @Schema(implementation = EmployeeResponseDto.class))),
        @ApiResponse( responseCode = "404", description = "Empleado no encontrado",content = @Content(mediaType = "application/json", schema = @Schema( example = "{ \"message\": \"Empleado no encontrado\"}")))
    })

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable int id){
        return new ResponseEntity<EmployeeResponseDto>(service.findById(id), HttpStatus.OK);
    }


    // post
    @Operation(summary = "Crear empleado",description = "Creación de empleado y asignación a una o varias oficinas",
               requestBody =@io.swagger.v3.oas.annotations.parameters.RequestBody(description="Datos del nuevo empleado",required = true,content = @Content(mediaType = "application/json",  schema = @Schema(implementation = EmployeeRequestDto.class))) )
    @ApiResponses(value={
        @ApiResponse(responseCode = "201" ,content = @Content(schema = @Schema(example = "{ \"message\": \"Empleado Registrado\"}"))),
        @ApiResponse( responseCode = "409",content = @Content(mediaType = "application/json", schema = @Schema( example = "{ \"message\": \"El Personal con DNI 767988xx Ya esta Registrado\"}")))
    })
    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody @Valid EmployeeRequestDto office){
        return new ResponseEntity<Map<String,String>>(service.create(office), HttpStatus.CREATED);
    }


    // put 

    @Operation(summary = "Actualizar empleado",description = "Actualización del empleado por su Id",
     requestBody =@io.swagger.v3.oas.annotations.parameters.RequestBody(description="Datos del nuevo empleado",required = true,content = @Content(mediaType = "application/json",  schema = @Schema(implementation = EmployeeRequestDto.class))) )
    @ApiResponses(value={
        @ApiResponse(responseCode = "200" ,content = @Content(schema = @Schema(example = "{ \"message\": \"Empleado Actualizado\"}"))),
         @ApiResponse( responseCode = "404", description = "Empleado no encontrado",content = @Content(mediaType = "application/json", schema = @Schema( example = "{ \"message\": \"Empleado no encontrado\"}")))
    })

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable int id, @RequestBody @Valid EmployeeRequestDto office){
        return new ResponseEntity<Map<String,String>>(service.update(id, office), HttpStatus.OK);
    }


    // delete
    @Operation(summary = "Eliminar empleado",description = "Eliminación del empleado por su Id")
    @ApiResponses(value={
        @ApiResponse(responseCode = "200" ,content = @Content(schema = @Schema(example = "{ \"message\": \"Empleado Actualizado\"}"))),
        @ApiResponse( responseCode = "404", description = "Empleado no encontrado",content = @Content(mediaType = "application/json", schema = @Schema( example = "{ \"message\": \"Empleado no encontrado\"}"))),
        @ApiResponse( responseCode = "403", description = "Privilegios Denegado",content = @Content(mediaType = "application/json", schema = @Schema( example = "{ \"message\": \"Usuario no cumple con los privilegios de Administrador\"}")))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id){
        return new ResponseEntity<Map<String,String>>(service.delete(id), HttpStatus.OK);
    }
}
