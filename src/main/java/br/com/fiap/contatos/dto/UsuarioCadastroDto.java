package br.com.fiap.contatos.dto;

import br.com.fiap.contatos.model.UsuarioRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UsuarioCadastroDto(
        Long id,

        @NotBlank(message = "O campo nome é obrigatório")
        String nome,

        @Email
        @NotBlank(message = "O campo email é obrigatório")
        String email,

        @Size(min = 6, max = 20, message = "A senha deve conter entre 6 e 20 caracteres.")
        @NotBlank(message = "O campo senha é obrigatório")
        String senha,

        UsuarioRole role
) {
}
