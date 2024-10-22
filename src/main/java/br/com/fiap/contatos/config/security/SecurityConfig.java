package br.com.fiap.contatos.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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
                // # Configuração das autorizações de requisições Http.
                .authorizeHttpRequests(authorize -> authorize
                        // # Configura que tipo de método http vai ser configurado
                        // e o local da EndPoint a ser configurado
                        .requestMatchers(HttpMethod.GET, "/api/contatos")
                        // # Ele permite que todos os roles façam essa requisição.
                        .permitAll()
                        // # E todos os outros tipo de requests
                        .anyRequest()
                        // # Precisa de autenticação para ser realizado.
                        .authenticated()
                )
                // # Use o .build para finalizar a construção do método.
                .build();
    }
}
