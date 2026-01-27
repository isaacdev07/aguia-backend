package aguia.history.drakes.services;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import aguia.history.drakes.domain.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;


@Service
public class TokenService {
    
   @Value("${api.security.token.secret}")
    private String secret;

    // 1. Gerar Token (Cria o crachá)
    public String generateToken(User user) {
        try {
            return Jwts.builder()
                    .setIssuer("aguia-history-api") // Nome da sua aplicação
                    .setSubject(user.getEmail())    // <--- ATUALIZADO: Usamos o email aqui
                    .setExpiration(genExpirationDate()) // Validade
                    .signWith(getSigningKey(), SignatureAlgorithm.HS256) // Assinatura
                    .compact();
        } catch (RuntimeException exception) {
            throw new RuntimeException("Erro ao gerar token JWT", exception);
        }
    }

    // 2. Validar Token (Lê o crachá e devolve o email do usuário)
    public String validateToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject(); // Devolve o email que estava guardado
        } catch (RuntimeException exception) {
            return ""; // Se for inválido, retorna vazio
        }
    }

    // Auxiliar: Define validade de 2 horas
    private Date genExpirationDate() {
        return Date.from(LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00")));
    }

    // Auxiliar: Transforma a String da senha em uma Chave Criptográfica
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

}
