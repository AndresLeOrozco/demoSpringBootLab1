package login.example.demoSpringBootLab1.service;

import login.example.demoSpringBootLab1.service.CalcularCitas.CitaHorario;
import login.example.demoSpringBootLab1.service.CalcularCitas.Dia;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class Citas {

    private List<CitaHorario> EstimarCitasDia(String rango, int frecuencia) {
        List<CitaHorario> citaHorarios = new ArrayList<>();
        String[] rangos = rango.split(",");

        for (String nodo : rangos) {
            String[] dias = nodo.split("-");
            int inicio = Integer.parseInt(dias[0]);
            int fin = Integer.parseInt(dias[1]);
            citaHorarios.addAll(EstimarCitasDia(inicio, fin, frecuencia));
        }
        return citaHorarios;
    }

    private List<CitaHorario> EstimarCitasDia(int inicio, int fin, int frecuencia) {
        List<CitaHorario> citaHorarios = new ArrayList<>();
        inicio *= 60;
        fin *= 60;

        while (inicio + frecuencia <= fin) {
            citaHorarios.add(new CitaHorario(inicio, inicio + frecuencia));
            inicio += frecuencia;
        }

        return citaHorarios;
    }

    public List<Dia> EstimarSemanaCitas(String horario, int frecuencia) {
        List<Dia> agenda = new ArrayList<>();
        String[] dias = horario.split(";");
        int diaContador = 1;

        for (String nodo : dias) {
            Dia dia = new Dia();
            dia.setNombre(diaContador++);
            if (!nodo.isBlank()) {
                dia.setCitas(EstimarCitasDia(nodo, frecuencia));
            }
            agenda.add(dia);
        }
        return agenda;
    }
}
