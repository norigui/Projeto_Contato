package br.com.fiap.contatos.config.security;

import br.com.fiap.contatos.model.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    // # Cria a palavra secreta para ser usada na criptografia.
    @Value("${chave.secreta}")
    private String palavraSecreta;

    // # Método para gerar um token.
    public String gerarToken(Usuario usuario) {

        // # Usamos o try para caso aconteça um erro na hora de gerar o token.
        try {
            // # Prepara a palavra secreta no objeto para criptografar usando o método HMAC256.
            Algorithm algorithm = Algorithm.HMAC256(palavraSecreta);
            // # Cria um token usando o JWT.
            String token = JWT
                    // # Método para preparar a criação do token.
                    .create()
                    // # Método para definir quem emitiu o token.
                    .withIssuer("contatos")
                    // # A entidade a quem pertence o token.
                    .withSubject(usuario.getEmail())
                    // # Método para definir a que horário o token expira.
                    .withExpiresAt(gerarDataExpiracao())
                    // # Método para assinar o token com a palavra chave e a criptografia.
                    .sign(algorithm);

            return  token;

        } catch (JWTCreationException erro) {
            throw  new RuntimeException("Não foi possível gerar o token.");
        }
    }

    // # Método para validar o token.
    public String validarToken(String token) {
        try {
            // # Precisamos passar o mesmo método e a palavra secreta para validar o token.
            Algorithm algorithm = Algorithm.HMAC256(palavraSecreta);
            return JWT
                    // # Método que recupera o método e a palavra secreta utilizada na criptografia.
                    .require(algorithm)
                    // # O emissor do token.
                    .withIssuer("contatos")
                    // # Método que compila todas as informações.
                    .build()
                    // # Método para verificar o hash do token.
                    .verify(token)
                    // # Método para pegar o dono do token.
                    .getSubject();
        } catch (JWTVerificationException erro) {
            return "";
        }
    }

    // # Método para gerar um horário futuro a partir do horário atual.
    public Instant gerarDataExpiracao() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }

}
