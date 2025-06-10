package login.example.demoSpringBootLab1.service;
import login.example.demoSpringBootLab1.model.Cita;
import login.example.demoSpringBootLab1.repository.CitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class CitaServiceImpl implements CitaService {

    @Autowired
    private CitaRepository citaRepository;

    @Autowired
    private CitaAutoGeneradorService citaAutoGeneradorService;

    @Override
    public List<Cita> obtenerCitasPorFecha(LocalDate fecha) {
        return citaRepository.findByFecha(fecha);
    }

    @Override
    public Cita guardarCita(Cita cita) {
        return citaRepository.save(cita);
    }

    @Override
    public void eliminarCita(Long id) {
        citaRepository.deleteById(id);
    }

    @Override
    public void generarCitasAutomaticamente() {
        citaAutoGeneradorService.generarCitasProximosTresDias();
    }
}
