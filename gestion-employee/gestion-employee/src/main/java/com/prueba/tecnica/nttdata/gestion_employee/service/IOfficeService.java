package com.prueba.tecnica.nttdata.gestion_employee.service;
import com.prueba.tecnica.nttdata.gestion_employee.model.dtos.*;
import java.util.*;


public interface IOfficeService {

    List<OfficeResponseDto> listAll();
    List<EmployeeResponseDto> listEmployeeByOffice(int officeId);
    OfficeResponseDto findById(int id);
    Map<String,String> create(OfficeRequestDto office);
    Map<String,String> createEmployeeByOffice(int officeId,int employeeId);
    Map<String,String> update(int id, OfficeRequestDto office);
    Map<String,String> delete(int id);
    Map<String,String> deleteEmployeeByOffice(int officeId,int employeeId);

}
