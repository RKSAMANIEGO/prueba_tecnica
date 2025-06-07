package com.prueba.tecnica.nttdata.gestion_employee.model;

import java.time.LocalDate;
import java.util.*;
import jakarta.persistence.*;
import lombok.*;
import lombok.Builder.Default;

@Entity
@Table(name="employee")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CEmployee {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 80 , nullable = false)
    private String name;

    @Column(length = 11 , nullable=true)
    private String phone;

    @Column(length = 8 , nullable = false)
    private String dni;

    @Column(length = 100, nullable = true)
    private String direction;

    @Column(nullable = false)
    private LocalDate birthday;

    @Default
    @Column(name = "is_active")
    private Boolean isActive=true;

    @ManyToMany
    @JoinTable(
        name = "employee_office",
        joinColumns =@JoinColumn(name="employee_id") ,
        inverseJoinColumns =@JoinColumn(name="office_id") 
    )
    @Default
    private Set<COffice> offices = new HashSet<>();
}
