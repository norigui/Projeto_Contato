package br.com.fiap.contatos.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record ContaCadastroDto(
        Long id,

        @NotBlank(message = "Nome obrigatório")
        String nome,

        @Size(min = 6, max = 20, message = "A senha deve conter entre 6 e 20 caracteres")
        @NotBlank(message = "Senha obrigatório")
        String senha,

        @Email(message = "Formato do email inválido")
        @NotBlank(message = "Email obrigatório")
        String email,

        @NotNull(message = "Data de nascimento obrigatório")
        LocalDate dataNascimento
) {
}
