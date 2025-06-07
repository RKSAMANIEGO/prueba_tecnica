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
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/employee")
@RequiredArgsConstructor
public class EmployeeController {
    
    private final IEmployeeService service;

    @GetMapping("")
    public ResponseEntity<List<EmployeeResponseDto>> listAll(){
        return new ResponseEntity<List<EmployeeResponseDto>>(service.listAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable int id){
        return new ResponseEntity<EmployeeResponseDto>(service.findById(id), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody @Valid EmployeeRequestDto office){
        return new ResponseEntity<Map<String,String>>(service.create(office), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable int id, @RequestBody @Valid EmployeeRequestDto office){
        return new ResponseEntity<Map<String,String>>(service.update(id, office), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id){
        return new ResponseEntity<Map<String,String>>(service.delete(id), HttpStatus.OK);
    }
}
