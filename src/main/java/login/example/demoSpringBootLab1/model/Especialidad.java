package login.example.demoSpringBootLab1.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "especialidad")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Especialidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "especialidad_id", nullable = false)
    private Integer id;

    @Size(max = 45)
    @Column(name = "especialidad_nombre", length = 45)
    private String especialidadNombre;
}
