package dev.rodrigovasconcelos.financasapp.service;

import dev.rodrigovasconcelos.financasapp.dto.TransacaoDto;
import dev.rodrigovasconcelos.financasapp.dto.TransacaoSalvarDto;

import java.util.Set;

public interface TransacaoService {
    Set<TransacaoDto> listar(Long carteiraId, int anoTransacao, int mesTransacao);

    TransacaoDto salvar(TransacaoSalvarDto transacaoSalvarDto);

    void remover(Long transacaoId);

    TransacaoDto atualizar(Long transacaoId, TransacaoSalvarDto transacaoSalvarDto);
}
