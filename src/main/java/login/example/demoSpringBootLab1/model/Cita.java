package login.example.demoSpringBootLab1.model;
import jakarta.persistence.*;
import login.example.demoSpringBootLab1.service.Citas;
import login.example.demoSpringBootLab1.service.CalcularCitas.Dia;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "cita")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @Column(name = "hora_inicio", nullable = false)
    private LocalTime horaInicio;

    @Column(name = "estado", nullable = false)
    private String estado = "DISPONIBLE";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id")
    private Usuario paciente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medico_id", nullable = false)
    private Medico medico;


    public static List<Dia> generarAgenda(String horarioSemanal, int frecuencia) {
        return new Citas().EstimarSemanaCitas(horarioSemanal, frecuencia);
    }

}
