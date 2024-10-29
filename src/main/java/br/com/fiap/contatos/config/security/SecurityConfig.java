package br.com.fiap.contatos.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// # O @Configuration identifica para o spring boot que essa é uma Classe do tipo Configuração.
// # O @EnableWebSecurity habilita as funções de segurança websecurity.
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private VerificarToken verificarToken;

    // # @Bean Ele fala para o spring boot que ele tem um objeto que precisa ser gerenciado.
    @Bean
    // # Cria um filtro de segurança Http
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        // # Desabilita o Cross-site Request Forgery.
        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        // # Ele configura a autenticação para o tipo STATELESS.
        httpSecurity.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        // # Configuração das autorizações de requisições Http.
        httpSecurity.authorizeHttpRequests(authorize -> authorize
                        // #  .requestMatchers Configura que tipo de método http vai ser configurado
                        // e o local da EndPoint a ser configurado
                        // # O .permitAll() permite que todos os roles façam essa requisição.
                        .requestMatchers(HttpMethod.GET, "/api/contatos").hasAnyRole("ADMIN", "USER")
                        // # .hasRole() indica qual role vai poder acessar os endpoints.
                        .requestMatchers(HttpMethod.POST, "api/contatos").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "api/usuario").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "api/usuario").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "auth/register").permitAll()
                        .requestMatchers(HttpMethod.POST, "auth/login").permitAll()
                        // # E todos os outros tipo de requests
                        .anyRequest()
                        // # Precisa de autenticação para ser realizado.
                        .authenticated()
                )
                // # Adiciona um filtro antes de executar o código.
                .addFilterBefore(
                        verificarToken,
                        UsernamePasswordAuthenticationFilter.class
                );
        // # Use o .build para finalizar a construção do método.
        return httpSecurity.build();
    }

    // # Ele retorna um objeto do tipo AuthenticationManager usando o AuthenticationConfiguration para gerenciar a autenticação
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    // # O PasswordEncoder criptografia a senha utilizando o algorítimo hash bcrypt.
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
