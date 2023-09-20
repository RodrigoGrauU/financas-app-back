package dev.rodrigovasconcelos.financasapp.service;

import dev.rodrigovasconcelos.financasapp.dto.ResumoMesDto;

public interface ResumoService {

    ResumoMesDto obterResumoMes(Integer ano, Integer mes, Long usuarioId);
}
