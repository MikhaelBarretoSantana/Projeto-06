package com.KL.projeto06.Service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.KL.projeto06.Exception.ErroAutenticacao;
import com.KL.projeto06.Exception.RegraNegocioException;
import com.KL.projeto06.Model.ModelEntity.Usuario;
import com.KL.projeto06.Repository.UsuarioRepository;
import com.KL.projeto06.Service.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private UsuarioRepository repository;

    public UsuarioServiceImpl(UsuarioRepository repository) {
        super();
        this.repository = repository;
    }

    @Override
    public Usuario autenticar(String email, String senha) {
        Optional<Usuario> usuario = repository.findByEmail(email);
        
        if(!usuario.isPresent()) {
            throw new ErroAutenticacao("Usuario não encontrado para o email informado");
        }
        if(!usuario.get().getPassword().equals(senha)) {
            throw new ErroAutenticacao("Senha inválida");
        }

        return usuario.get();
    }

    @Override
    @Transactional
    public Usuario salvarUsuario(Usuario usuario) {
        validarEmail(usuario.getEmail());
        return repository.save(usuario);
    }

    @Override
    public void validarEmail(String email) {
        Boolean existe = repository.existsByEmail(email);
        if (existe) {
            throw new RegraNegocioException("Já existe um usuario com este email");
        }
    }

    @Override
    public Optional<Usuario> obterPorId(Long id) {
        return repository.findById(id);
    }

}
