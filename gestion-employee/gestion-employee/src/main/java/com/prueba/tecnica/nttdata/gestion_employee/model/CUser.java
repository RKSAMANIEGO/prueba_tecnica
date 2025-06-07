package com.prueba.tecnica.nttdata.gestion_employee.model;
import com.prueba.tecnica.nttdata.gestion_employee.model.Enum.RoleEnum;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CUser {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 100, nullable = false)
    private String email;

    @Column(length = 255, nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(length = 50 , nullable = false)
    @Builder.Default
    private RoleEnum role=RoleEnum.GERENTE;

    @Builder.Default
    @Column(name = "is_enabled")
    private Boolean isEnabled=true;

}
