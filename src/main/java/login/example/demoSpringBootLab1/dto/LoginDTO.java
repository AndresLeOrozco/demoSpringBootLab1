package login.example.demoSpringBootLab1.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDTO {
    private String usuarioId; // Cambia `username` por `usuarioId`
    private String clave;

    public LoginDTO() {}

    @Override
    public String toString() {
        return "LoginDTO { usuarioId='" + usuarioId + "', password='" + clave+ "' }";
    }
}
