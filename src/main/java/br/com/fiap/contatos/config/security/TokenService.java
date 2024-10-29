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
        try {  // # Usamos o try para caso aconteça um erro na hora de gerar o token.
            Algorithm algorithm = Algorithm.HMAC256(palavraSecreta); // # Prepara a palavra secreta no objeto para criptografar usando o método HMAC256.
            String token = JWT  // # Cria um token usando o JWT.
                    .create()  // # Método para preparar a criação do token.
                    .withIssuer("contatos") // # Método para definir quem emitiu o token.
                    .withSubject(usuario.getEmail()) // # A entidade a quem pertence o token.
                    .withExpiresAt(gerarDataExpiracao()) // # Método para definir a que horário o token expira.
                    .sign(algorithm); // # Método para assinar o token com a palavra chave e a criptografia.

            return  token;

        } catch (JWTCreationException erro) {
            throw  new RuntimeException("Não foi possível gerar o token.");
        }
    }

    // # Método para validar o token.
    public String validarToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(palavraSecreta); // # Precisamos passar o mesmo método e a palavra secreta para validar o token.
            return JWT
                    .require(algorithm) // # Método que recupera o método e a palavra secreta utilizada na criptografia.
                    .withIssuer("contatos") // # O emissor do token.
                    .build() // # Método que compila todas as informações.
                    .verify(token) // # Método para verificar o hash do token.
                    .getSubject(); // # Método para pegar o dono do token.
        } catch (JWTVerificationException erro) {
            return "";
        }
    }

    // # Método para gerar um horário futuro a partir do horário atual.
    public Instant gerarDataExpiracao() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }

}
