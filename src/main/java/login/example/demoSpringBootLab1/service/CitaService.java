package login.example.demoSpringBootLab1.service;


import login.example.demoSpringBootLab1.model.Cita;
import java.time.LocalDate;
import java.util.List;

public interface CitaService {
    List<Cita> obtenerCitasPorFecha(LocalDate fecha);
    Cita guardarCita(Cita cita);
    void eliminarCita(Long id);
    void generarCitasAutomaticamente();  // Método que usará `CitaAutoGeneradorService`
}
