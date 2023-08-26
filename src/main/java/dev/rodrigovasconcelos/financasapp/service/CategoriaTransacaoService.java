package dev.rodrigovasconcelos.financasapp.service;

import dev.rodrigovasconcelos.financasapp.dto.CategoriaTransacaoDto;
import dev.rodrigovasconcelos.financasapp.entity.CategoriaTransacao;

import java.util.Set;

public interface CategoriaTransacaoService {
    Set<CategoriaTransacaoDto> listar(Long usuarioId);

    CategoriaTransacaoDto adicionar(CategoriaTransacaoDto categoriaTransacaoDto);

    CategoriaTransacaoDto atualizar(Long categoriaTransacaoId,CategoriaTransacaoDto categoriaTransacaoDto);

    void remover(Long categoriaTransacaoId);

    CategoriaTransacao buscarOuFalhar(Long id);
}
