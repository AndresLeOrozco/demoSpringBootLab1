package login.example.demoSpringBootLab1.repository;

import login.example.demoSpringBootLab1.model.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PerfilRepository extends JpaRepository<Perfil, Integer> {
    Perfil findByPerfilNombre(String perfilNombre);
}
