package login.example.demoSpringBootLab1.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "permisosperfil")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Permisosperfil {

    @EmbeddedId
    private PermisosperfilId id;

    @MapsId("perfilId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "perfil_id", nullable = false)
    private Perfil perfil;

    @MapsId("permisoId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "permiso_id", nullable = false)
    private Permiso permiso;
}
