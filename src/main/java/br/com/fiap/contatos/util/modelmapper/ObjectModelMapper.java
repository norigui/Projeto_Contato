package br.com.fiap.contatos.util.modelmapper;

import br.com.fiap.contatos.dto.ContatoExibirDto;
import br.com.fiap.contatos.model.Contato;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ObjectModelMapper {

    private final ModelMapper modelMapper = new ModelMapper();




}
