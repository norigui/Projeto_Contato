package br.com.fiap.contatos.controller;

import br.com.fiap.contatos.config.security.TokenService;
import br.com.fiap.contatos.config.security.VerificarToken;
import br.com.fiap.contatos.dto.LoginDto;
import br.com.fiap.contatos.dto.UsuarioCadastroDto;
import br.com.fiap.contatos.dto.UsuarioExibirDto;
import br.com.fiap.contatos.model.Usuario;
import br.com.fiap.contatos.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

// # Falar para o Rest Controlar esta classe
@RestController
@RequestMapping("/auth")
public class AuthController {

    // # Chamar o AuthenticationManager pra gerenciar o Autenticador.
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioService usuarioService;

    // # Use o @PostMapping pq o cliente vai enviar os dados pro controlador.
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid LoginDto loginDto) {

        //# Ele representa o nome do usuário e a senha.
        UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(
                // # Inserindo o campo do usuário e o da senha para o autenticador.
                loginDto.email(),
                loginDto.senha()
        );

        // # Autentica um objeto que contém um nome de usuário e senha
        Authentication auth = authenticationManager.authenticate(usernamePassword);

        String token = tokenService.gerarToken((Usuario) auth.getPrincipal());

        // # Ele compilar e se der certo retorna um ok.
        return ResponseEntity.ok(token);
    }

    // # Retorna uma mensagem que foi criada.
    @ResponseStatus(HttpStatus.CREATED)
    // # Local do endpoint a ser registrar.
    @PostMapping("/register")
    // # Cadastra o usuário usando o UsuarioCadastroDto e retorna um objeto do tipo UsuarioExibirDto.
    public UsuarioExibirDto registrar(@RequestBody @Valid UsuarioCadastroDto usuarioCadastroDto) {
        // # Instanciando um objeto do tipo UsuarioExibirDto.
        UsuarioExibirDto usuarioExibirDto = null;
        // # Gravando no banco de dados o objeto usuarioCadastroDto e salvando as propriedades no objeto usuarioExibirDto.
        usuarioExibirDto = usuarioService.gravar(usuarioCadastroDto);
        // # Retorna o objeto usuarioExibirDto.
        return usuarioExibirDto;
    }

}
