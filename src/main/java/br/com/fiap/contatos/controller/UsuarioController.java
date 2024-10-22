package br.com.fiap.contatos.controller;

import br.com.fiap.contatos.dto.UsuarioCadastroDto;
import br.com.fiap.contatos.dto.UsuarioExibirDto;
import br.com.fiap.contatos.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    @RequestMapping("/usuario")
    public UsuarioExibirDto gravar(@RequestBody @Valid UsuarioCadastroDto usuarioCadastroDto) {
        return usuarioService.gravar(usuarioCadastroDto);
    }



}
