package com.prueba.tecnica.nttdata.gestion_employee.service.impl;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.prueba.tecnica.nttdata.gestion_employee.model.CEmployee;
import com.prueba.tecnica.nttdata.gestion_employee.model.COffice;
import com.prueba.tecnica.nttdata.gestion_employee.model.dtos.EmployeeResponseDto;
import com.prueba.tecnica.nttdata.gestion_employee.model.dtos.OfficeRequestDto;
import com.prueba.tecnica.nttdata.gestion_employee.model.dtos.OfficeResponseDto;
import com.prueba.tecnica.nttdata.gestion_employee.repository.IEmployeeRepository;
import com.prueba.tecnica.nttdata.gestion_employee.repository.IOfficeRepository;
import com.prueba.tecnica.nttdata.gestion_employee.service.IOfficeService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class COfficeImpl implements IOfficeService{

    private final IOfficeRepository repository;
    private final IEmployeeRepository employeeRepository;

    @Override
    public List<OfficeResponseDto> listAll() {
        return repository.findAll()
                        .stream()
                        .filter(office -> office.getIsActive())
                        .map(office-> new OfficeResponseDto(office.getId(),office.getName()))
                        .toList();
    }

    @Override
    public List<EmployeeResponseDto> listEmployeeByOffice(int officeId) {
       COffice officeFound = repository.findByIdAndIsActive(officeId,true).orElseThrow(()-> new EntityNotFoundException("La Oficina con ID "+officeId+" No Existe"));
    
        return officeFound.getEmployees().stream()
                     .filter(employee -> employee.getIsActive())
                     .map(this::convertEmployeeToDtoResponse)
                     .collect(Collectors.toList());
    }

    @Override
    public OfficeResponseDto findById(int id) {
        COffice officeFound = repository.findByIdAndIsActive(id,true).orElseThrow(()-> new EntityNotFoundException("La Oficina con ID "+id+" No Existe"));
        return new OfficeResponseDto(officeFound.getId(), officeFound.getName());
    }

    @Override
    public Map<String, String> create(OfficeRequestDto office) {
        
        if(repository.findByName(office.name()).isPresent()){
            throw new IllegalArgumentException("La Officina "+office.name()+" Ya Existe");
        }

        COffice officeNew = COffice.builder().name(office.name()).build();
        repository.save(officeNew);

        return Map.of("message","Oficina Registrada");
    }

    @Override
    public Map<String, String> update(int id, OfficeRequestDto office) {
       COffice officeFound = repository.findByIdAndIsActive(id,true).orElseThrow(()-> new EntityNotFoundException("La Oficina con ID "+id+" No Existe"));

       officeFound.setName(office.name() !=null ? office.name() : officeFound.getName());
       repository.save(officeFound);

       return Map.of("message","Oficina Actualizada");
    }

    @Override
    public Map<String, String> delete(int id) {
        COffice officeFound = repository.findByIdAndIsActive(id,true).orElseThrow(()-> new EntityNotFoundException("La Oficina con ID "+id+" No Existe"));
        officeFound.setIsActive(false);
        repository.save(officeFound);
        return Map.of("message","Oficina Eliminada");
    }


    @Override
    public Map<String, String> deleteEmployeeByOffice(int officeId, int employeeId) {
        COffice officeFound = repository.findByIdAndIsActive(officeId,true).orElseThrow(()-> new EntityNotFoundException("La Oficina con ID "+officeId+" No Existe"));
        CEmployee employee = employeeRepository.findById(employeeId).orElseThrow(() -> new EntityNotFoundException("Empleado no encontrado"));

        if (!employee.getOffices().contains(officeFound)) {
            throw new IllegalStateException("El empleado no pertenece a esta oficina");
        }

        employee.getOffices().remove(officeFound);
        employeeRepository.save(employee);
        return Map.of("message", "Empleado eliminado de la oficina correctamente");
    }


    private EmployeeResponseDto convertEmployeeToDtoResponse(CEmployee employee) {
        return new EmployeeResponseDto(
            employee.getId(),
            employee.getName(),
            employee.getPhone(),
            employee.getDni(),
            employee.getDirection(),
            employee.getBirthday(),
            employee.getOffices().stream()
                .map(office -> new OfficeResponseDto(office.getId(), office.getName()))
                .collect(Collectors.toSet())
        );
    }

    @Override
    public Map<String, String> createEmployeeByOffice(int officeId, int employeeId) {
        COffice office = repository.findById(officeId).orElseThrow(() -> new EntityNotFoundException("Oficina no encontrada"));
        CEmployee employee = employeeRepository.findById(employeeId).orElseThrow(() -> new EntityNotFoundException("Empleado no encontrado"));
        
        if (employee.getOffices().contains(office)) {
            throw new IllegalStateException("El empleado ya está asociado a esta oficina");
        }
        employee.getOffices().add(office);
        office.getEmployees().add(employee);

        employeeRepository.save(employee);
        repository.save(office);
        return Map.of("message", "Empleado agregado a la oficina correctamente");
    }
    
}
