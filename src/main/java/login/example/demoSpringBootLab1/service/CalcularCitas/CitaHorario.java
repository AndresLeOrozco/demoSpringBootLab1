package login.example.demoSpringBootLab1.service.CalcularCitas;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CitaHorario {
    private int inicio;
    private int fin;
    private String horainicio;
    private String horafin;

    public CitaHorario(int inicio, int fin) {
        this.inicio = inicio;
        this.fin = fin;
        this.horainicio = convertMinutesHour(inicio);
        this.horafin = convertMinutesHour(fin);
    }

    public String convertMinutesHour(String minutes) {
        return convertMinutesHour(Integer.parseInt(minutes));
    }

    public String convertMinutesHour(int minutes) {
        int hour = minutes / 60;
        int minute = minutes % 60;
        return String.format("%d:%02d", hour, minute);
    }
}
