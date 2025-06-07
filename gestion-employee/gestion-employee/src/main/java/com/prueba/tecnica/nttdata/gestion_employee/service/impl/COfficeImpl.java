package com.prueba.tecnica.nttdata.gestion_employee.service.impl;
import java.util.*;
import org.springframework.stereotype.Service;
import com.prueba.tecnica.nttdata.gestion_employee.model.COffice;
import com.prueba.tecnica.nttdata.gestion_employee.model.dtos.OfficeRequestDto;
import com.prueba.tecnica.nttdata.gestion_employee.model.dtos.OfficeResponseDto;
import com.prueba.tecnica.nttdata.gestion_employee.repository.IOfficeRepository;
import com.prueba.tecnica.nttdata.gestion_employee.service.IOfficeService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class COfficeImpl implements IOfficeService{

    private final IOfficeRepository repository;

    @Override
    public List<OfficeResponseDto> listAll() {
        return repository.findAll()
                        .stream()
                        .filter(office -> office.getIsActive())
                        .map(office-> new OfficeResponseDto(office.getId(),office.getName()))
                        .toList();
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
    
}
