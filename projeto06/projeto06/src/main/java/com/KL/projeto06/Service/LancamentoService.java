package com.KL.projeto06.Service;

import java.util.List;

import com.KL.projeto06.Model.ModelEntity.Lancamentos;

public interface LancamentoService {
    
    Lancamentos salvar(Lancamentos lancamento);

    Lancamentos atualizar (Lancamentos lancamento);

    void deletar (Lancamentos lancamento);

    List<Lancamentos> buscar(Lancamentos lancamentoFiltro);

    void atualizarStatus(Lancamentos lancamento, StatusLancamentos status);

    void validar(Lancamentos lancamento);

}
