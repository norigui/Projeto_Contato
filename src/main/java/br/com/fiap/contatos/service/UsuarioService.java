package br.com.fiap.contatos.service;

import br.com.fiap.contatos.dto.UsuarioCadastroDto;
import br.com.fiap.contatos.dto.UsuarioExibirDto;
import br.com.fiap.contatos.exception.UsuarioNaoEncontradoException;
import br.com.fiap.contatos.model.Usuario;
import br.com.fiap.contatos.repository.UsuarioRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public UsuarioExibirDto gravar(UsuarioCadastroDto usuarioCadastroDto) {

        // # Pega a senha do usuário encoda dentro do BCryptPasswordEncoder para ser criptografada e salva no objeto
        // # senhaCrypt.
        String senhaCrypt = new BCryptPasswordEncoder().encode(usuarioCadastroDto.senha());
        Usuario usuario = new Usuario();
        BeanUtils.copyProperties(usuarioCadastroDto, usuario);
        // # Faz o set da senha criptografada na senha do usuário.
        usuario.setSenha(senhaCrypt);
        Usuario usuarioSalvo = usuarioRepository.save(usuario);
        return new UsuarioExibirDto(usuarioSalvo);
    }

    public Page<UsuarioExibirDto> listarTodosUsuarios(Pageable paginacao) {
        return usuarioRepository.findAll(paginacao).map(UsuarioExibirDto::new);
    }

    public UsuarioExibirDto buscarPorNome(String nome) {

        Optional<Usuario> usuarioOptional = usuarioRepository.findByNome(nome);

        if (usuarioOptional.isPresent()) {
            return new UsuarioExibirDto(usuarioOptional.get());
        } else {
            throw new UsuarioNaoEncontradoException("Usuário não encontrado.");
        }
    }

    public UsuarioExibirDto buscarPorId(Long id) {

        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);

        if (usuarioOptional.isPresent()) {
            return new UsuarioExibirDto(usuarioOptional.get());
        } else {
            throw new UsuarioNaoEncontradoException("Usuario não encontrado");
        }

    }

}
