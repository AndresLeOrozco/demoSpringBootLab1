package login.example.demoSpringBootLab1.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "perfiles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Perfil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "perfil_id", nullable = false)
    private Integer id;

    @Size(max = 45)
    @Column(name = "perfil_nombre", length = 45, unique = true)
    private String perfilNombre;

    @OneToMany(mappedBy = "perfil", fetch = FetchType.LAZY)
    private List<Permisosperfil> permisosperfils;
}
