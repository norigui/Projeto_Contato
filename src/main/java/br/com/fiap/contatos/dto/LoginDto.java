package br.com.fiap.contatos.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginDto(

        @NotBlank(message = "O email é obrigatório.")
        @Email(message = "O email não é válido.")
        String email,

        @NotBlank(message = "A senha é obrigatória.")
        @Size(min = 6, max = 20, message = "A senha deve conter entre 6 e 20 caracteres.")
        String senha
) {
}
