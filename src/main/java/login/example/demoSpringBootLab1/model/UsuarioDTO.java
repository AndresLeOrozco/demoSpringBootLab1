package login.example.demoSpringBootLab1.model;

public class UsuarioDTO {
    private String usuarioId;
    private String nombre;
    private String apellido;
    private String estado;
    private String perfilNombre;


    public UsuarioDTO(Usuario usuario) {
        this.usuarioId = usuario.getUsuarioId();
        this.nombre = usuario.getNombre();
        this.apellido = usuario.getApellido();
        this.estado = usuario.getEstado();
        this.perfilNombre = usuario.getPerfil().getPerfilNombre();
    }

    // Getters y setters si no usas Lombok
}

