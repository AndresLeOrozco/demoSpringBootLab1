package login.example.demoSpringBootLab1.service;

import jakarta.transaction.Transactional;
import login.example.demoSpringBootLab1.model.Permisosperfil;
import login.example.demoSpringBootLab1.model.Usuario;
import login.example.demoSpringBootLab1.repository.PerfilRepository;
import login.example.demoSpringBootLab1.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioDetailsServiceImpl implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("🛠️ Intentando cargar usuario por username: " + username);

        Optional<Usuario> usuarioOpt = usuarioRepository.findByUsuarioId(username);

        if (usuarioOpt.isEmpty()) {
            System.out.println("❌ Usuario no encontrado en la BD: " + username);
            throw new UsernameNotFoundException("Usuario no encontrado: " + username);
        }

        Usuario usuario = usuarioOpt.get();
        System.out.println("✅ Usuario encontrado: " + usuario.getUsuarioId() + " | Estado: " + usuario.getEstado());

        // Verificar si la cuenta está pendiente de aprobación
        if ("Pendiente".equalsIgnoreCase(usuario.getEstado())) {
            System.out.println("⚠️ Usuario no aprobado aún. Estado: Pendiente");
            throw new UsernameNotFoundException("El usuario no ha sido aprobado aún. Estado: Pendiente");
        }

        // Cargar permisos del perfil
        List<Permisosperfil> permisos = usuario.getPerfil().getPermisosperfils();
        System.out.println("🔎 Permisos del usuario:");

        permisos.forEach(p -> {
            if (p.getPermiso() != null) {
                System.out.println("   - " + p.getPermiso().getPermisosNombre());
            } else {
                System.out.println("   - ⚠️ Permiso NULL (revisar relaciones en BD)");
            }
        });

        // Convertir permisos a authorities para Spring Security
        List<GrantedAuthority> authorities = permisos.stream()
                .map(p -> new SimpleGrantedAuthority(p.getPermiso().getPermisosNombre()))
                .collect(Collectors.toList());

        System.out.println("🔐 Usuario autenticado con roles: " + authorities);

        return new org.springframework.security.core.userdetails.User(
                usuario.getUsuarioId(),
                usuario.getClave(), // sin cifrar (¡asegúrate de encriptarla!)
                authorities
        );
    }

}
