package com.KL.projeto06.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.KL.projeto06.Model.ModelEntity.Lancamentos;
import com.KL.projeto06.Model.ModelEnum.StatusLancamento;

public interface LancamentoService {
    
    Lancamentos salvar(Lancamentos lancamento);

    Lancamentos atualizar (Lancamentos lancamento);

    void deletar (Lancamentos lancamento);

    List<Lancamentos> buscar(Lancamentos lancamentoFiltro);

    void atualizarStatus(Lancamentos lancamento, StatusLancamento status);

    void validar(Lancamentos lancamento);

    Optional<Lancamentos> obterPorId(Long id);

    BigDecimal obterSaldoPorUsuario(Long id);

}
