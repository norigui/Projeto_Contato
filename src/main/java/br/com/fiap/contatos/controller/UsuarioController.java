package br.com.fiap.contatos.controller;

import br.com.fiap.contatos.dto.UsuarioExibirDto;
import br.com.fiap.contatos.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

//    @PostMapping("/usuario")
//    @ResponseStatus(HttpStatus.CREATED)
//    public UsuarioExibirDto gravar(@RequestBody @Valid UsuarioCadastroDto usuarioCadastroDto) {
//        return usuarioService.gravar(usuarioCadastroDto);
//    }

    @GetMapping("/usuario")
    @ResponseStatus(HttpStatus.OK)
    public Page<UsuarioExibirDto> listarTodosUsuarios(Pageable paginacao) {
        return usuarioService.listarTodosUsuarios(paginacao);
    }

    @GetMapping(value = "/usuario", params = "nome")
    @ResponseStatus(HttpStatus.OK)
    public UsuarioExibirDto buscarPorNome(@RequestParam String nome) {
        return usuarioService.buscarPorNome(nome);
    }

    @GetMapping(value = "/usuario", params = "id")
    @ResponseStatus(HttpStatus.OK)
    public UsuarioExibirDto buscarPorId(@RequestParam Long id) {
        return usuarioService.buscarPorId(id);
    }

}
