package login.example.demoSpringBootLab1.repository;

import login.example.demoSpringBootLab1.model.Permisosperfil;
import login.example.demoSpringBootLab1.model.PermisosperfilId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermisosperfilRepository extends JpaRepository<Permisosperfil, PermisosperfilId> {
}
