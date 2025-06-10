package login.example.demoSpringBootLab1.service.CalcularCitas;

import java.util.List;

public class Dia {

    String Nombre;
    List<CitaHorario> citaHorarios;

    public Dia()
    {}

    public Dia(String nombre, List<CitaHorario> citaHorarios)
    {
        this.Nombre = nombre;
        this.citaHorarios = citaHorarios;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public void setNombre(int dia) {
        Nombre = GetDayName(dia);
    }

    public List<CitaHorario> getCitas() {
        return citaHorarios;
    }

    public void setCitas(List<CitaHorario> citaHorarios) {
        this.citaHorarios = citaHorarios;
    }

    private String GetDayName(int day)
    {
        switch (day)
        {
            case 1:
                return "Lunes";
            case 2:
                return "Martes";
            case 3:
                return "Miercoles";
            case 4:
                return "Jueves";
            case 5:
                return "Viernes";
            case 6:
                return "Sabado";
            case 7:
                return "Domingo";
        }
        return "";

    }

}
