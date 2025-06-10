package login.example.demoSpringBootLab1.model;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PermisosperfilId implements Serializable {
    private static final long serialVersionUID = 1624350476509533909L;

    @NotNull
    @Column(name = "perfil_id", nullable = false)
    private Integer perfilId;

    @NotNull
    @Column(name = "permiso_id", nullable = false)
    private Integer permisoId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PermisosperfilId entity = (PermisosperfilId) o;
        return Objects.equals(perfilId, entity.perfilId) &&
                Objects.equals(permisoId, entity.permisoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(perfilId, permisoId);
    }
}
