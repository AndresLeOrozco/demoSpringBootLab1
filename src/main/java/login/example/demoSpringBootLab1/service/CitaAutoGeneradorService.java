package login.example.demoSpringBootLab1.service;
import login.example.demoSpringBootLab1.model.Cita;
import login.example.demoSpringBootLab1.model.Medico;
import login.example.demoSpringBootLab1.repository.CitaRepository;
import login.example.demoSpringBootLab1.repository.MedicoRepository;
import login.example.demoSpringBootLab1.service.CalcularCitas.CitaHorario;
import login.example.demoSpringBootLab1.service.CalcularCitas.Dia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class CitaAutoGeneradorService {

    @Autowired
    private CitaRepository citaRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    public void generarCitasProximosTresDias() {
        List<Medico> medicos = medicoRepository.findAll();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:mm");

        for (Medico medico : medicos) {
            String horario = medico.getHorariosemanal();
            Integer frecuencia = medico.getFrecuencia();

            if (horario == null || horario.isBlank() || frecuencia == null || frecuencia <= 0) continue;

            Citas generador = new Citas();
            List<Dia> agendaSemanal = generador.EstimarSemanaCitas(horario, frecuencia);

            LocalDate hoy = LocalDate.now();

            for (int i = 0; i < 3; i++) {
                LocalDate fecha = hoy.plusDays(i);
                int diaSemana = fecha.getDayOfWeek().getValue(); // Lunes = 1, Domingo = 7

                if (diaSemana > agendaSemanal.size()) continue;

                Dia dia = agendaSemanal.get(diaSemana - 1);
                if (dia.getCitas() == null || dia.getCitas().isEmpty()) continue;

                for (CitaHorario citaHorario : dia.getCitas()) {
                    LocalTime horaInicio = LocalTime.parse(citaHorario.getHorainicio(), formatter);

                    boolean existe = citaRepository.existsByMedicoAndFechaAndHoraInicio(medico, fecha, horaInicio);
                    if (!existe) {
                        Cita nueva = new Cita();
                        nueva.setFecha(fecha);
                        nueva.setHoraInicio(horaInicio);
                        nueva.setEstado("DISPONIBLE");
                        nueva.setMedico(medico);
                        nueva.setPaciente(null);
                        citaRepository.save(nueva);
                    }
                }
            }
        }
    }
}
