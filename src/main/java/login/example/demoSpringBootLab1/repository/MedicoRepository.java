package login.example.demoSpringBootLab1.repository;

import login.example.demoSpringBootLab1.model.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicoRepository extends JpaRepository<Medico, String> {

    // Buscar médicos por especialidad
    List<Medico> findByEspecialidad_Id(Integer especialidadId);

    // Buscar médicos por localidad
    List<Medico> findByLocalidad_Id(Integer localidadId);

    // Buscar médicos por frecuencia de citas
    List<Medico> findByFrecuencia(Integer frecuencia);

    // Buscar médicos que contengan una palabra clave en presentación
    List<Medico> findByPresentacionContaining(String palabraClave);

}
