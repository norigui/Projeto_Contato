package br.com.fiap.contatos.dto;

import br.com.fiap.contatos.model.Contato;

import java.time.LocalDate;

public record ContatoExibirDto(
        Long id,
        String nome,
        String email,
        LocalDate dataNascimento
) {

    public ContatoExibirDto(Contato contato) {
        this (
                contato.getId(),
                contato.getNome(),
                contato.getEmail(),
                contato.getDataNascimento()
        );
    }

}
