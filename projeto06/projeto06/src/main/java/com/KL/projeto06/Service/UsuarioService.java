package com.KL.projeto06.Service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.KL.projeto06.Model.ModelEntity.Usuario;

@Service
public interface UsuarioService {
    
    Usuario autenticar(String email, String senha);

    Usuario salvarUsuario(Usuario usuario);

    void validarEmail(String email);

    Optional<Usuario> obterPorId(Long id);
}
