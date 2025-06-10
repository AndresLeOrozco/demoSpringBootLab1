package login.example.demoSpringBootLab1.repository;

import login.example.demoSpringBootLab1.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, String> {

    // Buscar usuarios por perfil y estado (Ej: "MEDICO", "ACTIVO")
    List<Usuario> findByPerfil_PerfilNombreAndEstado(String perfilNombre, String estado);

    // Buscar usuario por su ID
    Optional<Usuario> findByUsuarioId(String usuarioId);

    List<Usuario> findByPerfil_PerfilNombre(String perfilNombre);
}
