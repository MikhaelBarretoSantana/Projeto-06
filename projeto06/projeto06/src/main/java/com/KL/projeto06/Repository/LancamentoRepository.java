package com.KL.projeto06.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.KL.projeto06.Model.ModelEntity.Lancamentos;

public interface LancamentoRepository extends JpaRepository<Lancamentos, Long> {
    
}
