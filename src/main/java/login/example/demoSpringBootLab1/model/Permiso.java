package login.example.demoSpringBootLab1.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "permisos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Permiso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "permisos_id", nullable = false)
    private Integer id;

    @Size(max = 45)
    @Column(name = "permisos_nombre", length = 45)
    private String permisosNombre;
}
