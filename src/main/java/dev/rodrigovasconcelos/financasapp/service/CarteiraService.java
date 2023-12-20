package dev.rodrigovasconcelos.financasapp.service;

import dev.rodrigovasconcelos.financasapp.dto.CarteiraComMesesAnoDto;
import dev.rodrigovasconcelos.financasapp.dto.CarteiraDto;
import dev.rodrigovasconcelos.financasapp.entity.Carteira;
import dev.rodrigovasconcelos.financasapp.entity.Usuario;

import java.util.Set;

public interface CarteiraService {

    CarteiraDto salvarCarteira(CarteiraDto carteira, Usuario usuario);

    Set<CarteiraComMesesAnoDto> listaCarteiras(Long usuarioId);

    void removerCarteira(Long carteiraId);

    Carteira buscarOuFalhar(Long carteiraId);
}
