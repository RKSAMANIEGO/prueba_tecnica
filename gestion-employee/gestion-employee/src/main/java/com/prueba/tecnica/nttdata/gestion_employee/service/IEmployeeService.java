package com.prueba.tecnica.nttdata.gestion_employee.service;
import com.prueba.tecnica.nttdata.gestion_employee.model.dtos.EmployeeRequestDto;
import com.prueba.tecnica.nttdata.gestion_employee.model.dtos.EmployeeResponseDto;
import java.util.*;


public interface IEmployeeService {
    
    List<EmployeeResponseDto> listAll();
    EmployeeResponseDto findById(int id);
    Map<String,String> create(EmployeeRequestDto employee);
    Map<String,String> update(int id, EmployeeRequestDto employee);
    Map<String,String> delete(int id);

}

