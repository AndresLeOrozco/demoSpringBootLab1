package login.example.demoSpringBootLab1.controller;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import login.example.demoSpringBootLab1.model.Medico;
import login.example.demoSpringBootLab1.model.Usuario;
import login.example.demoSpringBootLab1.repository.EspecialidadRepository;
import login.example.demoSpringBootLab1.repository.LocalidadRepository;
import login.example.demoSpringBootLab1.repository.MedicoRepository;
import login.example.demoSpringBootLab1.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/medico")
public class PerfilController {

    @Autowired
    private EspecialidadRepository especialidadRepo;

    @Autowired
    private LocalidadRepository localidadRepo;

    @Autowired
    private MedicoRepository medicoRepo;

    @Autowired
    private UsuarioRepository usuarioRepo;

    @PersistenceContext
    private EntityManager entityManager;

    @GetMapping("/configurar")
    public String mostrarFormulario(Model model, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null) return "redirect:/login";


        model.addAttribute("especialidades", especialidadRepo.findAll());
        model.addAttribute("localidades", localidadRepo.findAll());

        model.addAttribute("diasSemana", List.of("Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo"));

        model.addAttribute("medico", new Medico());

        return "configurar_perfil";
    }

    @PostMapping("/configurar")
    @Transactional
    public String guardarPerfilMedico(@RequestParam("especialidad") String especialidad,
                                      @RequestParam("localidad") String localidad,
                                      @RequestParam("costoconsulta") String costo,
                                      @RequestParam("frecuencia") Integer frecuencia,
                                      @RequestParam("presentacion") String presentacion,
                                      @RequestParam("foto") MultipartFile foto,
                                      HttpSession session,
                                      @RequestParam MultiValueMap<String, String> formParams) throws IOException {

        Usuario usuario = usuarioRepo.findById(((Usuario) session.getAttribute("usuario")).getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (usuario == null || !usuario.getPerfil().getPerfilNombre().equalsIgnoreCase("medico")) {
            return "redirect:/login";
        }

        StringBuilder horario = new StringBuilder();
        for (int i = 0; i < 7; i++) {
            String r1 = formParams.getFirst("rango1_" + i);
            String r2 = formParams.getFirst("rango2_" + i);

            if ((r1 != null && !r1.isEmpty()) || (r2 != null && !r2.isEmpty())) {
                if (r1 != null && !r1.isEmpty()) {
                    horario.append(r1);
                }
                if (r2 != null && !r2.isEmpty()) {
                    if (r1 != null && !r1.isEmpty()) horario.append(",");
                    horario.append(r2);
                }
            }
            horario.append(";");
        }

        Medico medico = medicoRepo.findById(usuario.getUsuarioId()).orElse(null);

        if (medico == null) {
            medico = new Medico();
            medico.setUsuario(usuario);

        } else {
            // Hibernate necesita que este objeto venga del contexto de persistencia
            medico = entityManager.merge(medico); // <-- esto sincroniza correctamente
        }
        medico.setCostoconsulta(costo);
        medico.setFrecuencia(frecuencia);
        medico.setHorariosemanal(horario.toString());
        medico.setPresentacion(presentacion);
        medico.setEspecialidad(especialidadRepo.findById(Integer.parseInt(especialidad)).orElse(null));
        medico.setLocalidad(localidadRepo.findById(Integer.parseInt(localidad)).orElse(null));

        // ✅ Guardar imagen en carpeta local
        if (!foto.isEmpty()) {
            String ruta = System.getProperty("user.dir") + "/src/main/resources/static/img/medicos/";
            File carpeta = new File(ruta);

            // 👇 Crear la carpeta si no existe
            if (!carpeta.exists()) {
                carpeta.mkdirs();
            }

            String nombreArchivo = usuario.getUsuarioId() + "_" + foto.getOriginalFilename();
            File destino = new File(carpeta, nombreArchivo);
            foto.transferTo(destino);

            medico.setFoto("/img/medicos/" + nombreArchivo);
        }

        medicoRepo.save(medico);

        if ("Perfil".equalsIgnoreCase(usuario.getEstado())) {
            usuario.setEstado("Activo");
            usuarioRepo.save(usuario);
            session.setAttribute("usuario", usuario);
        }

        return "redirect:/citas/mishistorico";
    }
}
