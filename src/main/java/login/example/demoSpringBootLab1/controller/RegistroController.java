package login.example.demoSpringBootLab1.controller;


import login.example.demoSpringBootLab1.model.Usuario;
import login.example.demoSpringBootLab1.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/registro")
public class RegistroController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "registro"; // Redirige a la vista registro.html
    }

    @PostMapping
    public ResponseEntity<String> registrarUsuario(@ModelAttribute Usuario usuario, @RequestParam String tipo, @RequestParam String confirmarClave, Model model) {
        try {
            usuarioService.registrarUsuario(usuario, tipo, confirmarClave);
            return ResponseEntity.ok("Registro exitoso");
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("usuario", usuario); // Para mantener los datos ingresados
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }
}

