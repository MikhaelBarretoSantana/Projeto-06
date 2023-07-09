package com.KL.projeto06.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.KL.projeto06.Model.ModelEntity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    Boolean existsByEmail(String email);

    Optional<Usuario> findByEmail(String email);
}
