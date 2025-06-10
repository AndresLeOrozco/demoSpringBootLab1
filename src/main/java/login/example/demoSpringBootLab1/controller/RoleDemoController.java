package login.example.demoSpringBootLab1.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// En un nuevo controlador o uno existente
@RestController
@RequestMapping("/private")
public class RoleDemoController {

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> adminOnly() {
        return ResponseEntity.ok("<h1>Bienvenido administrador</h1>");
    }

    @GetMapping("/user")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<?> userOnly() {
        return ResponseEntity.ok("<h1>Bienvenido usuario</h1>");
    }

    @GetMapping
    public ResponseEntity<?> privateAccess() {
        return ResponseEntity.ok("<h2>Acceso privado para usuarios autenticados unicamente</h2>");
    }
}
