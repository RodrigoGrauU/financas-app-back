package dev.rodrigovasconcelos.financasapp.service;

import dev.rodrigovasconcelos.financasapp.dto.CarteiraComMesesAnoDto;
import dev.rodrigovasconcelos.financasapp.dto.CarteiraDto;
import dev.rodrigovasconcelos.financasapp.entity.Carteira;

import java.util.Set;

public interface CarteiraService {

    CarteiraDto salvarCarteira(CarteiraDto carteira);

    Set<CarteiraComMesesAnoDto> listaCarteiras(Long usuarioId);

    void removerCarteira(Long carteiraId);

    Carteira buscarOuFalhar(Long carteiraId);
}
