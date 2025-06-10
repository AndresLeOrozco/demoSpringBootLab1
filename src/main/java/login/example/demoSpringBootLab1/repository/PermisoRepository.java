package login.example.demoSpringBootLab1.repository;

import login.example.demoSpringBootLab1.model.Permiso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermisoRepository extends JpaRepository<Permiso, Integer> {

    // Buscar permisos por nombre
    List<Permiso> findByPermisosNombreContaining(String nombre);
    Permiso findByPermisosNombre(String permisosNombre);
}
