package com.prueba.tecnica.nttdata.gestion_employee.model;
import jakarta.persistence.*;
import lombok.*;
import lombok.Builder.Default;

@Entity
@Table(name="office")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class COffice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 100, nullable = false)
    private String name;
   
    @Default
    @Column(name = "is_active")
    private Boolean isActive=true;
}
