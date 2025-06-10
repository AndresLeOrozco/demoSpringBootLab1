package login.example.demoSpringBootLab1;

import jakarta.annotation.PostConstruct;
import login.example.demoSpringBootLab1.model.Usuario;
import login.example.demoSpringBootLab1.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@SpringBootApplication
@RequiredArgsConstructor
public class DemoSpringBootLab1Application {

	private final UsuarioService usuarioService;
	private final PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(DemoSpringBootLab1Application.class, args);
	}

	@Bean
	public CommandLineRunner initUsuarios() {
		return args -> {
			String adminId = "admin90";

			Optional<Usuario> usuarioOpt = usuarioService.buscarPorId(adminId);

			if (usuarioOpt.isEmpty()) {
				Usuario admin = new Usuario();
				admin.setUsuarioId(adminId);
				admin.setClave("admin123");  // En texto plano, será cifrada al guardar
				usuarioService.registrarUsuario(admin, "medico", "admin123");

				System.out.println("Usuario admin creado con usuario: " + adminId + " y contraseña 'admin123'");
			} else {
				System.out.println("Usuario admin ya existe, no se crea.");
			}
		};
	}

}
