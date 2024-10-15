package br.com.fiap.contatos.repository;

import br.com.fiap.contatos.model.Contato;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ContatoRepository extends JpaRepository<Contato, Long> {

    public Optional<Contato> findByNome(String nome);

    public List<Contato> findByDataNascimentoBetween(LocalDate dataInical, LocalDate dataFinal);

}
