package login.example.demoSpringBootLab1.repository;

import login.example.demoSpringBootLab1.model.Cita;
import login.example.demoSpringBootLab1.model.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Long> {

    boolean existsByMedicoAndFechaAndHoraInicio(Medico medico, LocalDate fecha, LocalTime horaInicio);

    @Query("""
    SELECT c FROM Cita c 
    WHERE c.estado = 'DISPONIBLE' 
    AND c.fecha = :fecha 
    AND (:especialidadId IS NULL OR c.medico.especialidad.id = :especialidadId) 
    AND (:localidadId IS NULL OR c.medico.localidad.id = :localidadId)
    """)
    List<Cita> findDisponiblesPorDiaHoy(
            @Param("especialidadId") Integer especialidadId,
            @Param("localidadId") Integer localidadId,
            @Param("fecha") LocalDate fecha
    );

    List<Cita> findByFecha(LocalDate fecha);
}
