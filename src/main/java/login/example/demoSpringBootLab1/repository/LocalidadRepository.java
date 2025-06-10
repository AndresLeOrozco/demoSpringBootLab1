package login.example.demoSpringBootLab1.repository;

import login.example.demoSpringBootLab1.model.Localidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocalidadRepository extends JpaRepository<Localidad, Integer> {


}
