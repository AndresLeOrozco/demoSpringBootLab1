package login.example.demoSpringBootLab1.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Table(name = "usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @Size(max = 10)
    @Column(name = "usuario_id", nullable = false, length = 10)
    private String usuarioId;

    @Size(max = 45)
    @Column(name = "nombre", length = 45)
    private String nombre;

    @Size(max = 45)
    @Column(name = "apellido", length = 45)
    private String apellido;

    @Column(name = "clave", length = 255, nullable = false)
    private String clave;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "perfil_id")
    private Perfil perfil;

    @Size(max = 20)
    @Column(name = "estado", length = 20)
    private String estado;

    // Método para encriptar contraseña
    public void setClaveEncriptada(String clave, PasswordEncoder passwordEncoder) {
        this.clave = passwordEncoder.encode(clave);
    }
}
