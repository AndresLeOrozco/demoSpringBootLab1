package login.example.demoSpringBootLab1.controller;

import login.example.demoSpringBootLab1.model.Cita;
import login.example.demoSpringBootLab1.service.CitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/citas")
public class CitaController {

    @Autowired
    private CitaService citaService;

    @GetMapping("/fecha/{fecha}")
    public ResponseEntity<List<Cita>> obtenerCitasPorFecha(@PathVariable LocalDate fecha) {
        List<Cita> citas = citaService.obtenerCitasPorFecha(fecha);
        return ResponseEntity.ok(citas);
    }
}
