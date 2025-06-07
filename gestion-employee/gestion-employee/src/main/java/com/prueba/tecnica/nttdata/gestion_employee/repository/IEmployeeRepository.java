package com.prueba.tecnica.nttdata.gestion_employee.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.prueba.tecnica.nttdata.gestion_employee.model.CEmployee;

public interface IEmployeeRepository extends JpaRepository<CEmployee,Integer> {
    
    Optional<CEmployee> findByIdAndIsActive(int id, boolean getIsActive);
}
