package login.example.demoSpringBootLab1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicoInfoDTO {
    private String nombreCompletoUsuario;
    private String costoconsulta;
    private String localidadNombre; // El nombre de la localidad
    private String horariosemanal;
    private String estado;
    private String especialidad;
    private String presentacion;
    private String usuarioId;

}
