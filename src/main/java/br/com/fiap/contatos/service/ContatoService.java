package br.com.fiap.contatos.service;

import br.com.fiap.contatos.dto.ContaCadastroDto;
import br.com.fiap.contatos.dto.ContatoExibirDto;
import br.com.fiap.contatos.model.Contato;
import br.com.fiap.contatos.repository.ContatoRepository;
import br.com.fiap.contatos.util.modelmapper.ObjectModelMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class ContatoService {

    @Autowired
    private ContatoRepository contatoRepository;
    private ObjectModelMapper objectModelMapper;

    public ContatoExibirDto gravar(ContaCadastroDto contaCadastroDto) {
        Contato contato = new Contato();
        BeanUtils.copyProperties(contaCadastroDto, contato);
        return new ContatoExibirDto(contatoRepository.save(contato));
    }

    public ContatoExibirDto buscarPorId(Long id) {
        Optional<Contato> contatoOptional = contatoRepository.findById(id);

        if (contatoOptional.isPresent()) {
            return new ContatoExibirDto(contatoOptional.get());
        } else {
            throw new RuntimeException("Contato não encontrado");
        }
    }

    public List<ContatoExibirDto> listarTodosOsContato() {
        List<ContatoExibirDto> contatoExibirDtos = new ArrayList<>();
        List<Contato> contato = contatoRepository.findAll();

        return contatoExibirDtos = contato.stream().map(ContatoExibirDto::new).toList();
    }

    public void excluir(Long id) {
        Optional<Contato> contatoOptional = contatoRepository.findById(id);

        if (contatoOptional.isPresent()) {
            contatoRepository.delete(contatoOptional.get());
        } else {
            throw new RuntimeException("Contado não encontrado");
        }

    }

    public List<ContatoExibirDto> listarPorData(LocalDate dataInicial, LocalDate dataFinal) {
        List<ContatoExibirDto> contatoExibirDto = new ArrayList<>();
        List<Contato> contato = contatoRepository.findByDataNascimentoBetween(dataInicial, dataFinal);
        return contatoExibirDto = contato.stream().map(ContatoExibirDto::new).toList();
    }

    public ContatoExibirDto atualizarContato(ContaCadastroDto contaCadastroDto) {
        Contato contato = new Contato();
        BeanUtils.copyProperties(contaCadastroDto, contato);
        Optional<Contato> contatoOptional = contatoRepository.findById(contato.getId());

        if (contatoOptional.isPresent()) {
            return new ContatoExibirDto(contatoRepository.save(contato));
        } else {
            throw new RuntimeException("Contato não encontrado");
        }

    }

    public ContatoExibirDto buscarPorNome(String nome) {
        Optional<Contato> contatoOptional = contatoRepository.findByNome(nome);

        if (contatoOptional.isPresent()) {
            return new ContatoExibirDto(contatoOptional.get());
        } else {
            throw new RuntimeException("Contato não encontrado");
        }

    }

}
