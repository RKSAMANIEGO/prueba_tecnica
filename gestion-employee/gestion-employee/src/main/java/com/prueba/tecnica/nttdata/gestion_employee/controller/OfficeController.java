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
import com.prueba.tecnica.nttdata.gestion_employee.model.dtos.OfficeRequestDto;
import com.prueba.tecnica.nttdata.gestion_employee.model.dtos.OfficeResponseDto;
import com.prueba.tecnica.nttdata.gestion_employee.service.IOfficeService;
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
@RequestMapping("/api/office")
@RequiredArgsConstructor
@Tag(name = "Office", description = "Gestion de los oficinas")
public class OfficeController {
    
    private final IOfficeService service;

    //list all
    @Operation(summary = "Listar Oficinas", description = "Listado de Oficinas Activas")
    @ApiResponse(responseCode = "200", content = @Content(array = @ArraySchema(schema = @Schema(implementation = OfficeResponseDto.class))))
    @GetMapping("")
    public ResponseEntity<List<OfficeResponseDto>> listAll(){
        return new ResponseEntity<List<OfficeResponseDto>>(service.listAll(), HttpStatus.OK);
    }

    //find by id
    @Operation(summary = "Buscar por Oficina", description = "Buscar Oficinas por su Id")
    @ApiResponses(value={
        @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = OfficeResponseDto.class))),
        @ApiResponse( responseCode = "404",content = @Content(mediaType = "application/json", schema = @Schema( example = "{ \"message\": \"La Oficina con ID 5 No Existe\"}")))
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable int id){
        return new ResponseEntity<OfficeResponseDto>(service.findById(id), HttpStatus.OK);
    }

    //post
    @Operation(summary = "Crear Oficina",description = "Creación de las oficinas",
               requestBody =@io.swagger.v3.oas.annotations.parameters.RequestBody(description="Datos de la nueva oficina",required = true,content = @Content(mediaType = "application/json",  schema = @Schema(implementation = OfficeRequestDto.class))) )
    @ApiResponses(value={
        @ApiResponse(responseCode = "201" ,content = @Content(schema = @Schema(example = "{ \"message\": \"Oficina Registrada\"}"))),
        @ApiResponse( responseCode = "400",content = @Content(mediaType = "application/json", schema = @Schema( example = "{ \"message\": \"La Oficina ya se Encuentra Registrada\"}")))
    })
    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody @Valid OfficeRequestDto office){
        return new ResponseEntity<Map<String,String>>(service.create(office), HttpStatus.CREATED);
    }

    //put
    @Operation(summary = "Actualizar Oficina",description = "Actualización de las oficinas por su Id",
               requestBody =@io.swagger.v3.oas.annotations.parameters.RequestBody(description="Datos de la oficina ah Actualizar",required = true,content = @Content(mediaType = "application/json",  schema = @Schema(implementation = OfficeRequestDto.class))) )
    @ApiResponses(value={
        @ApiResponse(responseCode = "200" ,content = @Content(schema = @Schema(example = "{ \"message\": \"Oficina Actualizada\"}"))),
        @ApiResponse( responseCode = "404",content = @Content(mediaType = "application/json", schema = @Schema( example = "{ \"message\": \"La Oficina con ID 5 No Existe\"}")))
    })  

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable int id, @RequestBody @Valid OfficeRequestDto office){
        return new ResponseEntity<Map<String,String>>(service.update(id, office), HttpStatus.OK);
    }


    //delete
    @Operation(summary = "Eliminar Oficina", description = "Eliminación de las Oficinas por su Id")
    @ApiResponses(value={
        @ApiResponse(responseCode = "200" ,content = @Content(schema = @Schema(example = "{ \"message\": \"Oficina Eliminada\"}"))),
        @ApiResponse( responseCode = "404",content = @Content(mediaType = "application/json", schema = @Schema( example = "{ \"message\": \"La Oficina con ID 5 No Existe\"}"))),
        @ApiResponse( responseCode = "403",content = @Content(mediaType = "application/json", schema = @Schema( example = "{ \"message\": \"Usuario no cumple con los privilegios de Administrador\"}")))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id){
        return new ResponseEntity<Map<String,String>>(service.delete(id), HttpStatus.OK);
    }
}
