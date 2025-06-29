package login.example.demoSpringBootLab1.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "localidad")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Localidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "localidad_id", nullable = false)
    private Integer id;

    @Size(max = 45)
    @Column(name = "localidad_nombre", length = 45)
    private String localidadNombre;
}
