package com.prueba.tecnica.nttdata.gestion_employee.service.impl;
import java.util.*;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.prueba.tecnica.nttdata.gestion_employee.model.CEmployee;
import com.prueba.tecnica.nttdata.gestion_employee.model.COffice;
import com.prueba.tecnica.nttdata.gestion_employee.model.dtos.EmployeeRequestDto;
import com.prueba.tecnica.nttdata.gestion_employee.model.dtos.EmployeeResponseDto;
import com.prueba.tecnica.nttdata.gestion_employee.model.dtos.OfficeResponseDto;
import com.prueba.tecnica.nttdata.gestion_employee.repository.IEmployeeRepository;
import com.prueba.tecnica.nttdata.gestion_employee.repository.IOfficeRepository;
import com.prueba.tecnica.nttdata.gestion_employee.service.IEmployeeService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CEmployeeImpl implements IEmployeeService{
    
    private final IEmployeeRepository repository;
    private final IOfficeRepository officeRepository;

    @Override
    public List<EmployeeResponseDto> listAll() {
        return repository.findAll().stream()
                                    .filter(obj -> obj.getIsActive())
                                    .map(obj -> convertEmployeeToDtoResponse(obj))
                                    .toList();
    }

    @Override
    public EmployeeResponseDto findById(int id) {
        CEmployee employeeFound = repository.findByIdAndIsActive(id,true).orElseThrow(()->new EntityNotFoundException("El personal con ID "+id+" No Existe")); 
        return convertEmployeeToDtoResponse(employeeFound);
    }

    @Override
    public Map<String, String> create(EmployeeRequestDto employee) {
        repository.save(convertDtoToEmployee(employee));
        return Map.of("message","Personal Registrado");
    }

    @Override
    public Map<String, String> update(int id, EmployeeRequestDto employee) {
       CEmployee employeeFound = repository.findByIdAndIsActive(id,true).orElseThrow(()->new EntityNotFoundException("El personal con ID "+id+" No Existe"));  

       employeeFound.setName(employee.name() !=null ? employee.name() : employeeFound.getName());
       employeeFound.setPhone(employee.phone()!=null ? employee.phone() : employeeFound.getPhone());
       employeeFound.setDni(employee.dni() !=null ? employee.dni() : employeeFound.getDni());
       employeeFound.setDirection(employee.direction() !=null ? employee.direction() : employeeFound.getDirection());
       employeeFound.setBirthday(employee.birthday() !=null ? employee.birthday() : employeeFound.getBirthday());

       if(employee.offices() !=null){
            Set<COffice> listOffice=employee.offices().stream()
                                    .map(cod ->officeRepository.findById(cod).orElseThrow(()->new EntityNotFoundException("ID "+id+" Not Found")))
                                    .collect(Collectors.toSet());
            employeeFound.setOffices(listOffice);
       }

       repository.save(employeeFound);
       return Map.of("message","Personal Actualizado");
    }

    @Override
    public Map<String, String> delete(int id) {
        CEmployee employeeFound = repository.findByIdAndIsActive(id,true).orElseThrow(()->new EntityNotFoundException("El personal con ID "+id+" No Existe")); 
        employeeFound.setIsActive(false);
        repository.save(employeeFound);
        return Map.of("message","Personal Eliminado");
    }


    private EmployeeResponseDto convertEmployeeToDtoResponse(CEmployee obj){
        return new EmployeeResponseDto(obj.getId(), 
                                    obj.getName(), 
                                    obj.getPhone(), 
                                    obj.getDni(), 
                                    obj.getDirection(), 
                                    obj.getBirthday(),
                                    obj.getOffices().stream()
                                        .map(office -> new OfficeResponseDto(office.getId(),office.getName() ))
                                        .collect(Collectors.toSet()));
    }

    private CEmployee convertDtoToEmployee(EmployeeRequestDto employee){
        return CEmployee.builder()
                    .name(employee.name())
                    .phone(employee.phone())
                    .dni(employee.dni())
                    .direction(employee.direction())
                    .birthday(employee.birthday())
                    .offices( employee.offices().stream()
                                                .map(id ->officeRepository.findById(id).orElseThrow(()->new EntityNotFoundException("ID "+id+" Not Found")))
                                                .collect(Collectors.toSet()))
                    .build();
    }
}
