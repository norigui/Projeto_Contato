package br.com.fiap.contatos.controller;

import br.com.fiap.contatos.dto.ContaCadastroDto;
import br.com.fiap.contatos.dto.ContatoExibirDto;
import br.com.fiap.contatos.model.Contato;
import br.com.fiap.contatos.service.ContatoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ContatoController {

    @Autowired
    private ContatoService contatoService;

    // # O @ResponseStatus() Configura qual é o código que será retornado ao ser realizado a ação da endpoint.
    @ResponseStatus(HttpStatus.CREATED)
    // # O @PostMapping() configura o endpoint do tipo Post(Enviar). Insira dentro dos parêntesis o local do endpoint.
    @PostMapping("/contatos")
    // # O @RequestBody vai dizer para o Spring Boot para usar os objetos desse método como o conteúdo.
    // # O @Valid serve para usar as validações criadas na classe CadastroDto.
    public ContatoExibirDto gravar(@RequestBody @Valid ContaCadastroDto contaCadastroDto) {
        return contatoService.gravar(contaCadastroDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/contatos")
    public Page<ContatoExibirDto> listarTodosContatos(Pageable paginacao) {
        return contatoService.listarTodosOsContato(paginacao);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/contatos/id/{id}")
    public ContatoExibirDto buscarPorId(@PathVariable Long id) {
        return contatoService.buscarPorId(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/contatos", params = {"dataInicial", "dataFinal"})
    //  api/contatos?dataInicial=1900-01-01&dataFinal=2000-01-01
    public List<ContatoExibirDto> listarPorData(
            @RequestParam LocalDate dataInicial,
            @RequestParam LocalDate dataFinal) {
        return contatoService.listarPorData(dataInicial, dataFinal);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/contatos", params = "nome")
    public ContatoExibirDto buscarPorNome(@RequestParam String nome) {
        return contatoService.buscarContatoPeloNome(nome);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/contato", params = "email")
    public ContatoExibirDto buscarContatoPeloEmail(@RequestParam String email) {
        return contatoService.buscarContatoPeloEmail(email);
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
