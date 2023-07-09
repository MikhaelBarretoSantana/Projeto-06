package com.KL.projeto06.Service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.KL.projeto06.Exception.RegraNegocioException;
import com.KL.projeto06.Model.ModelEntity.Lancamentos;
import com.KL.projeto06.Model.ModelEnum.StatusLancamento;
import com.KL.projeto06.Repository.LancamentoRepository;
import com.KL.projeto06.Service.LancamentoService;
import com.KL.projeto06.Service.StatusLancamentos;

@Service
public class LancamentoServiceImpl implements LancamentoService {

    private LancamentoRepository repository;

    public LancamentoServiceImpl(LancamentoRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public Lancamentos salvar(Lancamentos lancamento) {
        validar(lancamento);
        lancamento.setStatus(StatusLancamento.PENDENTE);
        return repository.save(lancamento);
    }

    @Override
    @Transactional
    public Lancamentos atualizar(Lancamentos lancamento) {
        Objects.requireNonNull(lancamento.getId());
        return repository.save(lancamento);
    }

    @Override
    @Transactional
    public void deletar(Lancamentos lancamento) {
        Objects.requireNonNull(lancamento.getId());
        repository.delete(lancamento);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Lancamentos> buscar(Lancamentos lancamentoFiltro) {
        Example example = Example.of(lancamentoFiltro, ExampleMatcher.matching()
        .withIgnoreCase()
        .withStringMatcher(StringMatcher.CONTAINING));
        return repository.findAll(example);
    }

    @Override
    public void atualizarStatus(Lancamentos lancamento, StatusLancamentos status) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'atualizarStatus'");
    }

    @Override
    public void atualizarStatus(Lancamentos lancamento, StatusLancamentos status) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'atualizarStatus'");
    }

    @Override
    public void atualizarStatus(Lancamentos lancamento, StatusLancamentos status) {
        lancamento.setStatus(status);
        atualizar(lancamento);
    }

    @Override
    public void validar(Lancamentos lancamento) {
        
        if(lancamento.getDescricao() == null || lancamento.getDescricao().isBlank()) {
            throw new RegraNegocioException("Informe uma descrição válida");
        }
        
        if( lancamento.getMes() == null || lancamento.getMes() < 1 || lancamento.getMes() > 12) {
            throw new RegraNegocioException("Informe um mês válido");
        }

        if (lancamento.getAno() == null || lancamento.getAno().toString().length() != 4) {
            throw new RegraNegocioException("Informe um ano Válido");
        }

        if( lancamento.getUsuario() == null || lancamento.getUsuario().getId() == null) {
            throw new RegraNegocioException("Informe um usuario");
        }

        if( lancamento.getValor() == null || lancamento.getValor().compareTo(BigDecimal.ZERO) < 1) {
            throw new RegraNegocioException("Informe um valor válido");
        }

        if( lancamento.getTipo() == null) {
            throw new RegraNegocioException("Informe um tipo de lançamento");
        }

    }

}
