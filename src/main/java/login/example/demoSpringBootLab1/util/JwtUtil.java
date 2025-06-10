package login.example.demoSpringBootLab1.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import login.example.demoSpringBootLab1.model.Usuario;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class JwtUtil {

    private static final String SECRET_KEY = Base64.getEncoder()
            .encodeToString("claveSuperSeguraParaJwtQueTiene32Caracteres!".getBytes());
    private static final long EXPIRATION_TIME = 1000 * 60 * 60; // 1 hora

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(Base64.getDecoder().decode(SECRET_KEY));
    }

    public String generateToken(UserDetails userDetails, Usuario usuario) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("perfil", usuario.getPerfil().getPerfilNombre());
        claims.put("permisos", usuario.getPerfil().getPermisosperfils().stream()
                .map(p -> p.getPermiso().getPermisosNombre())
                .collect(Collectors.toList()));

        return createToken(claims, userDetails.getUsername());
    }

    private String createToken(Map<String, Object> claims, String subject) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + EXPIRATION_TIME);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        try {
            final String username = extractUsername(token);
            return username != null && username.equals(userDetails.getUsername()) && !isTokenExpired(token);
        } catch (JwtException e) {
            System.out.println("Error validando token: " + e.getMessage());
            return false;
        }
    }

    public String extractUsername(String token) {
        String username = extractClaim(token, Claims::getSubject);
        System.out.println("Username extraído del JWT: " + username);
        return username;
    }


    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = parseToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims parseToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (JwtException e) {
            System.out.println("Error parseando token JWT: " + e.getMessage());
            return null;
        }
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token) != null && extractExpiration(token).before(new Date());
    }
}
