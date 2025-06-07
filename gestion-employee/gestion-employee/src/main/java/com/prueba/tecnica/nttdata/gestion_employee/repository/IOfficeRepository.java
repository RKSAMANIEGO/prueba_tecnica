package com.prueba.tecnica.nttdata.gestion_employee.repository;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.prueba.tecnica.nttdata.gestion_employee.model.COffice;


public interface IOfficeRepository extends JpaRepository<COffice,Integer> {

    Optional<COffice> findByName(String name);
    Optional<COffice> findByIdAndIsActive(int id, boolean isActive);

}
