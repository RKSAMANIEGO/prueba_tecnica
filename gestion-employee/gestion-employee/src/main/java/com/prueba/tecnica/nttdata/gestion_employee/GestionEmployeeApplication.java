package com.prueba.tecnica.nttdata.gestion_employee;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.prueba.tecnica.nttdata.gestion_employee.model.CUser;
import com.prueba.tecnica.nttdata.gestion_employee.model.Enum.RoleEnum;
import com.prueba.tecnica.nttdata.gestion_employee.repository.IUserRepository;

import lombok.RequiredArgsConstructor;

@SpringBootApplication
@RequiredArgsConstructor
public class GestionEmployeeApplication implements CommandLineRunner {

	private final IUserRepository userRepository;
	private final PasswordEncoder pass;

	public static void main(String[] args) {
		SpringApplication.run(GestionEmployeeApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		//Created User 
		CUser userAdmin = CUser.builder()
							.email("enrike@gmail.com")
							.password(pass.encode("enrike123"))
							.role(RoleEnum.ADMIN)
							.build();
		CUser userGerente = CUser.builder()
							.email("stefano@gmail.com")
							.password(pass.encode("stefano123"))
							.role(RoleEnum.GERENTE)
							.build();

		if(userRepository.count() == 0){
			userRepository.saveAll(List.of(userAdmin,userGerente));
		}else{
			System.out.println("La Tabla Usuario ya cuenta Registros");
		}
		

	}

}
