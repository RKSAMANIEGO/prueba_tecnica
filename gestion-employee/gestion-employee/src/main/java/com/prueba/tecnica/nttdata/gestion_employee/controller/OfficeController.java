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
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/office")
@RequiredArgsConstructor
public class OfficeController {
    
    private final IOfficeService service;

    @GetMapping("")
    public ResponseEntity<List<OfficeResponseDto>> listAll(){
        return new ResponseEntity<List<OfficeResponseDto>>(service.listAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable int id){
        return new ResponseEntity<OfficeResponseDto>(service.findById(id), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody @Valid OfficeRequestDto office){
        return new ResponseEntity<Map<String,String>>(service.create(office), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable int id, @RequestBody @Valid OfficeRequestDto office){
        return new ResponseEntity<Map<String,String>>(service.update(id, office), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id){
        return new ResponseEntity<Map<String,String>>(service.delete(id), HttpStatus.OK);
    }
}
