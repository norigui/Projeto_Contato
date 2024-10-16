package br.com.fiap.contatos.repository;

import br.com.fiap.contatos.model.Contato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ContatoRepository extends JpaRepository<Contato, Long> {

    @Query("SELECT c FROM Contato c WHERE c.nome = :nome")
    Optional<Contato> buscarContatopeloNome(@Param("nome") String nome);

    Optional<Contato> findByNome(String nome);

    List<Contato> findByDataNascimentoBetween(LocalDate dataInical, LocalDate dataFinal);

    @Query("SELECT c FROM Contato c WHERE c.dataNascimento BETWEEN :dataInicial AND :dataFinal")
    List<Contato> listarAniversarianteDoPeriodo(@Param("dataInicial") LocalDate dataInicial,
                                                @Param("dataFinal") LocalDate dataFinal);

}
