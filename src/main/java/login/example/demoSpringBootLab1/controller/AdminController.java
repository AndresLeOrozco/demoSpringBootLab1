package login.example.demoSpringBootLab1.controller;


import login.example.demoSpringBootLab1.model.Medico;
import login.example.demoSpringBootLab1.model.MedicoInfoDTO;
import login.example.demoSpringBootLab1.model.Usuario;
import login.example.demoSpringBootLab1.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/aprobar-medicos")
    public List<MedicoInfoDTO> listarMedicos() {
        return usuarioService.obtenerTodosLosMedicos();
    }

    // ✅ Aprueba un médico y redirige de vuelta a la lista
    @PostMapping("/aprobar/{id}")
    public boolean aprobarMedico(@PathVariable String id) {
        usuarioService.aprobarMedico(id);
        return true; // Recarga la lista actualizada
    }
}

