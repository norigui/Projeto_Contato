package br.com.fiap.contatos.service;

import br.com.fiap.contatos.dto.ContaCadastroDto;
import br.com.fiap.contatos.dto.ContatoExibirDto;
import br.com.fiap.contatos.exception.UsuarioNaoEncontradoException;
import br.com.fiap.contatos.model.Contato;
import br.com.fiap.contatos.repository.ContatoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.*;

// # USE O @Service PARA O SPRING BOOT RECONHECER A CLASSE SENDO DO TIPO SERVICE
@Service
public class ContatoService {

    // # INJETANDO A INTERFACE REPOSITORY NA CLASSE.
    @Autowired
    private ContatoRepository contatoRepository;

    // # Esse método recebe um objeto do tipo CadastroDto para copiar os atributo do mesmo para o objeto Contato.
    // # Depois o Repository salva o objeto Contato para o banco de dados e converte em um Objeto ExibirDto.
    // # O retorno final é um objeto ExibirDto.
    public ContatoExibirDto gravar(ContaCadastroDto contaCadastroDto) {
        Contato contato = new Contato();
        // # COPIA O OBJETO PARA O OBJETO DTO
        BeanUtils.copyProperties(contaCadastroDto, contato);
        return new ContatoExibirDto(contatoRepository.save(contato));
    }

    public ContatoExibirDto buscarPorId(Long id) {
        Optional<Contato> contatoOptional = contatoRepository.findById(id);

        if (contatoOptional.isPresent()) {
            return new ContatoExibirDto(contatoOptional.get());
        } else {
            throw new UsuarioNaoEncontradoException("Contato não encontrado");
        }
    }

    // # CRIA UMA LISTA COM UMA PAGINACAO
    public Page<ContatoExibirDto> listarTodosOsContato(Pageable paginacao) {
        // # CONVERTE UMA LISTA PARA UMA LISTA DTO
        return contatoRepository
                .findAll(paginacao)
                .map(ContatoExibirDto::new);
    }

    public void excluir(Long id) {
        Optional<Contato> contatoOptional = contatoRepository.findById(id);

        if (contatoOptional.isPresent()) {
            contatoRepository.delete(contatoOptional.get());
        } else {
            throw new UsuarioNaoEncontradoException("Contado não encontrado");
        }

    }

    public List<ContatoExibirDto> listarPorData(LocalDate dataInicial, LocalDate dataFinal) {
        // # CONVERTE UMA LISTA PARA UMA LISTA DTO
        return contatoRepository
                .listarAniversarianteDoPeriodo(dataInicial, dataFinal)
                .stream()
                .map(ContatoExibirDto::new)
                .toList();
    }

//    public List<ContatoExibirDto> listarPorData(LocalDate dataInicial, LocalDate dataFinal) {
//        // # CONVERTE UMA LISTA PARA UMA LISTA DTO
//        List<ContatoExibirDto> contatoExibirDto = new ArrayList<>();
//        List<Contato> contato = contatoRepository.findByDataNascimentoBetween(dataInicial, dataFinal);
//        return contatoExibirDto = contato.stream().map(ContatoExibirDto::new).toList();
//    }

    public ContatoExibirDto atualizarContato(ContaCadastroDto contaCadastroDto) {
        Contato contato = new Contato();
        BeanUtils.copyProperties(contaCadastroDto, contato);
        Optional<Contato> contatoOptional = contatoRepository.findById(contato.getId());

        if (contatoOptional.isPresent()) {
            return new ContatoExibirDto(contatoRepository.save(contato));
        } else {
            throw new UsuarioNaoEncontradoException("Contato não encontrado");
        }

    }

    public ContatoExibirDto buscarContatoPeloNome(String nome) {
        Optional<Contato> contatoOptional = contatoRepository.buscarContatopeloNome(nome);

        if (contatoOptional.isPresent()) {
            return new ContatoExibirDto(contatoOptional.get());
        } else {
            throw new UsuarioNaoEncontradoException("Contato não encontrado");
        }

    }

    public ContatoExibirDto buscarContatoPeloEmail(String email) {
        Optional<Contato> contatoOptional = contatoRepository.findByEmail(email);

        if(contatoOptional.isPresent()) {
            return new ContatoExibirDto(contatoOptional.get());
        } else {
            throw new UsuarioNaoEncontradoException("Contato não encontrado");
        }
    }

//    public ContatoExibirDto buscarPorNome(String nome) {
//        Optional<Contato> contatoOptional = contatoRepository.findByNome(nome);
//
//        if (contatoOptional.isPresent()) {
//            return new ContatoExibirDto(contatoOptional.get());
//        } else {
//            throw new UsuarioNaoEncontradoException("Contato não encontrado");
//        }
//
//    }

}
