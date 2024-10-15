package br.com.fiap.contatos.controller;

import br.com.fiap.contatos.dto.ContaCadastroDto;
import br.com.fiap.contatos.dto.ContatoExibirDto;
import br.com.fiap.contatos.model.Contato;
import br.com.fiap.contatos.service.ContatoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ContatoController {

    @Autowired
    private ContatoService contatoService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/contatos")
    public ContatoExibirDto gravar(@RequestBody @Valid ContaCadastroDto contaCadastroDto) {
        return contatoService.gravar(contaCadastroDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/contatos")
    public List<ContatoExibirDto> listarTodosContatos() {
        return contatoService.listarTodosOsContato();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/contatos/id/{id}")
    public ContatoExibirDto buscarPorId(@PathVariable Long id) {
        return contatoService.buscarPorId(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/contatos/{dataInicial}/{dataFinal}")
    public List<Contato> listarPorData(
            @PathVariable LocalDate dataInicial,
            @PathVariable LocalDate dataFinal) {
        return contatoService.listarPorData(dataInicial, dataFinal);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/contatos/nome/{nome}")
    public ContatoExibirDto buscarPorNome(@PathVariable String nome) {
        return contatoService.buscarPorNome(nome);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/contatos/{id}")
    public void excluirContato(@PathVariable Long id) {
        contatoService.excluir(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/contatos")
    public ContatoExibirDto atualizarContato(@RequestBody ContaCadastroDto contaCadastroDto) {
        return contatoService.atualizarContato(contaCadastroDto);
    }

}
