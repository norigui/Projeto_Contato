package br.com.fiap.contatos.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

// # O @Configuration identifica para o spring boot que essa é uma Classe do tipo Configuração.
// # O @EnableWebSecurity habilita as funções de segurança websecurity.
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // # @Bean Ele fala para o spring boot que ele tem um objeto que precisa ser gerenciado.
    @Bean
    // # Cria um filtro de segurança Http
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        // # Desabilita o Cross-site Request Forgery.
        return httpSecurity.csrf(csrf -> csrf.disable())
                // # Ele configura a autenticação para o tipo STATELESS.
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }
}
