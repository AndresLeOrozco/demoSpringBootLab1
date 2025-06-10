package login.example.demoSpringBootLab1.service;

import jakarta.transaction.Transactional;
import login.example.demoSpringBootLab1.model.*;
import login.example.demoSpringBootLab1.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final MedicoRepository medicoRepository;
    private final PerfilRepository perfilRepository;
    private final PermisosperfilRepository permisosperfilRepository;
    private final PermisoRepository permisoRepository;
    private final PasswordEncoder passwordEncoder;  // <--- aquí
    private final LocalidadRepository localidadRepository;
    private final EspecialidadRepository especialidadRepository;
    


    @Transactional
    public Usuario getUsuarioConPerfil(String usuarioId) {
        Usuario usuario = usuarioRepository.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Forzar la carga de la relación lazy
        usuario.getPerfil().getPermisosperfils().size(); // Esto carga la lista


        return usuario;
    }

    @Transactional
    public void registrarUsuario(Usuario usuario, String tipo, String confirmarClave) {
        if (!usuario.getClave().equals(confirmarClave)) {
            throw new IllegalArgumentException("Las contraseñas no coinciden");
        }

        // Verifica que no exista un usuario con este id
        if (usuarioRepository.existsById(usuario.getUsuarioId())) {
            throw new RuntimeException("El usuario con id " + usuario.getUsuarioId() + " ya existe.");
        }

        // Cifrar la contraseña antes de guardar
        String claveCifrada = passwordEncoder.encode(usuario.getClave());
        usuario.setClave(claveCifrada);

        // Asignar perfil y estado según el tipo
        if ("medico".equals(tipo)) {
            usuario.setPerfil(perfilRepository.findByPerfilNombre("medico"));
            usuario.setEstado("Pendiente");
        } else {
            usuario.setPerfil(perfilRepository.findByPerfilNombre("paciente"));
            usuario.setEstado("Activo");
        }

        // Guardar el usuario
        usuarioRepository.save(usuario);

//        // Si el usuario es médico, se registra la entidad Medico asociada
//        if ("medico".equals(tipo)) {
//            Localidad localidad = localidadRepository.findById(1)
//                    .orElseThrow(() -> new RuntimeException("Localidad no encontrada"));
//            Especialidad especialidad = especialidadRepository.findById(1)
//                    .orElseThrow(() -> new RuntimeException("Especialidad no encontrada"));
//
//            Medico medico = new Medico();
//            // No asignar manualmente el id; se asignará a partir de usuario (con @MapsId)
//            medico.setCostoconsulta("");       // Valor por defecto
//            medico.setLocalidad(localidad);
//            medico.setEspecialidad(especialidad);
//            medico.setHorariosemanal("");       // Valor por defecto
//            medico.setFrecuencia(0);            // Valor por defecto
//            medico.setFoto("");                 // Valor por defecto
//            medico.setPresentacion("");         // Valor por defecto
//            medico.setUsuario(usuario);
//
//            medicoRepository.save(medico);
//        }
    }





    public Usuario autenticar(String usuarioId, String claveIngresada) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(usuarioId);
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            // Compara la contraseña sin cifrar con la cifrada usando passwordEncoder.matches()
            if (passwordEncoder.matches(claveIngresada, usuario.getClave())) {
                return usuario;
            }
        }
        return null;
    }

    public void aprobarMedico(String usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuario.setEstado("Perfil");

        usuarioRepository.save(usuario);
    }

    @Transactional
    public List<MedicoInfoDTO> obtenerTodosLosMedicos() {
        List<Medico> medicos = medicoRepository.findAll();
        List<MedicoInfoDTO> medicosInfo =  new ArrayList<>();
        medicos.forEach(medico -> {
            String nombre = (medico.getUsuario() != null && medico.getUsuario().getNombre() != null)
                    ? medico.getUsuario().getNombre() : "Sin nombre";
            medicosInfo.add(new MedicoInfoDTO(
                    nombre,
                    medico.getCostoconsulta(),
                    (medico.getLocalidad() != null ? medico.getLocalidad().getLocalidadNombre() : "Sin localidad"),
                    medico.getHorariosemanal(), medico.getUsuario().getEstado(), medico.getEspecialidad().getEspecialidadNombre(), medico.getPresentacion(), medico.getMedicoId()));
        });

        return medicosInfo;
    }


    public Optional<Usuario> buscarPorId(String usuarioId) {
        return usuarioRepository.findById(usuarioId);
    }
}

