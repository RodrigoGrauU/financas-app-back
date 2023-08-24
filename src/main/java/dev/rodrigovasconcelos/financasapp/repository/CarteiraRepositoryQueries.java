package dev.rodrigovasconcelos.financasapp.repository;

import dev.rodrigovasconcelos.financasapp.dto.AnoTransacaoDto;

import java.util.Set;

public interface CarteiraRepositoryQueries {
    Set<AnoTransacaoDto> find(Long... carteirasId);
}
