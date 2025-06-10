package login.example.demoSpringBootLab1.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecureController {

    @GetMapping("/api/secure")
    public String secureEndpoint() {
        return "Acceso autorizado a contenido seguro.";
    }
}
