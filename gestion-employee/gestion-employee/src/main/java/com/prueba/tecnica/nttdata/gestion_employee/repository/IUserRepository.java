package com.prueba.tecnica.nttdata.gestion_employee.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prueba.tecnica.nttdata.gestion_employee.model.CUser;

public interface IUserRepository extends JpaRepository<CUser,Integer>{
    
    Optional<CUser> findByEmail(String email);
}
