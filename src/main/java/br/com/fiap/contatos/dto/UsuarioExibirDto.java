package br.com.fiap.contatos.dto;


import br.com.fiap.contatos.model.Contato;
import br.com.fiap.contatos.model.Usuario;
import br.com.fiap.contatos.model.UsuarioRole;

public record UsuarioExibirDto(

        Long id,
        String nome,
        String email,
        UsuarioRole role
) {
    public UsuarioExibirDto(Usuario usuario) {
        this(
                usuario.getIdUsuario(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getRole()
        );
    }
}
