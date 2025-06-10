package login.example.demoSpringBootLab1.controller;

import login.example.demoSpringBootLab1.dto.LoginDTO;
import login.example.demoSpringBootLab1.model.Usuario;
import login.example.demoSpringBootLab1.repository.UsuarioRepository;
import login.example.demoSpringBootLab1.service.UsuarioService;
import login.example.demoSpringBootLab1.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UsuarioRepository usuarioRepository;
    private final UsuarioService usuarioService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO usuario) {
        System.out.println("🛠️ Intentando autenticar usuario: " + usuario.getUsuarioId() + " " +  usuario.getClave());

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(usuario.getUsuarioId(), usuario.getClave())
            );

            // Obtener detalles del usuario autenticado
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            System.out.println("✅ Usuario autenticado: " + userDetails.getUsername() );

            // Buscar el usuario en la base de datos
            Usuario usuarioDb = usuarioService.getUsuarioConPerfil(usuario.getUsuarioId());

            System.out.println("🔎 Usuario en BD: " + usuarioDb.getUsuarioId() + " | Estado uwu: " + usuarioDb.getEstado());

            // Generar JWT con datos correctos
            String token = jwtUtil.generateToken(userDetails, usuarioDb);
            System.out.println("🔐 JWT generado correctamente");

            // Construir respuesta
            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("perfil", usuarioDb.getPerfil().getPerfilNombre());

            return ResponseEntity.ok(response);
        } catch (BadCredentialsException ex) {
            System.out.println("❌ Error: Credenciales inválidas para usuario " + usuario.getUsuarioId());
            return ResponseEntity.status(401).body("Credenciales inválidas");
        } catch (Exception ex) {
            System.out.println("⚠️ Error inesperado en autenticación: " + ex.getMessage());
            return ResponseEntity.status(500).body("Error interno del servidor");
        }
    }
}

//    // 📌 Autenticar usuario y generar JWT
//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody LoginDTO usuario) {
//        System.out.println("Intentando autenticar usuario: " + usuario.getUsername());
//
//        try {
//            // Autenticación del usuario
//            Authentication authentication = authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(usuario.getUsername(), usuario.getPassword())
//            );
//
//            // Obtener detalles del usuario autenticado
//            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//
//            // Obtener usuario de la base de datos
//            Usuario usuarioDb = usuarioRepository.findByUsuarioId(usuario.getUsername())
//                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
//
//            // Generar JWT con los datos correctos
//            String token = jwtUtil.generateToken(usuarioDb);
//
//            // Construir la respuesta con el token y perfil
//            Map<String, Object> response = new HashMap<>();
//            response.put("token", token);
//            response.put("perfil", usuarioDb.getPerfil().getPerfilNombre());
//
//            return ResponseEntity.ok(response);
//        } catch (BadCredentialsException ex) {
//            return ResponseEntity.status(401).body("Credenciales inválidas");
//        }
//    }
//}
