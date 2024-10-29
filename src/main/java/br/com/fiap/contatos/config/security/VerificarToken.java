package br.com.fiap.contatos.config.security;

import br.com.fiap.contatos.repository.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
// # @Component serve para ser autogerenciado pelo Spring boot e também será possível injetar ele em outras classes.
public class VerificarToken extends OncePerRequestFilter { // # Ele extend o OncePerRequestFilter para o filtro ser executado apenas uma vez.

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(

            HttpServletRequest request, // # O Request extrai os dados da página.
            HttpServletResponse response, // # O response é a respota que será retornado.
            FilterChain filterChain)  // # filterchain são os filtros que serão adicionados.
            throws ServletException, IOException {


        String authorizationHeader = request.getHeader("Authorization"); // # Vai pegar o Header com o campo "Authorization".
        String token = "";

        if (authorizationHeader == null) { // Vai transformar o token em nula se o campo Authorization do header estiver nulo.
            token = null;
        } else {
            token = authorizationHeader.replace("Bearer", "").trim(); // # O método replace, troca a palavra "Bearer" por vazio "", e o método trim tira os espaços vazios.
            String login = tokenService.validarToken(token); // # Valida o token retirado do header e retorna o Username(email).
            UserDetails userDetails = usuarioRepository.findByEmail(login); // # Usa o Username obtido pelo login e procura o Username no banco de dados.

            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
            SecurityContextHolder
                    .getContext()
                    .setAuthentication(authenticationToken);
        }

        filterChain.doFilter(request, response);

    }
}
