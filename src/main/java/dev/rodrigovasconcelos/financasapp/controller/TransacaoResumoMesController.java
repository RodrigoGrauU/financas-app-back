package dev.rodrigovasconcelos.financasapp.controller;

import dev.rodrigovasconcelos.financasapp.dto.ResumoMesDto;
import dev.rodrigovasconcelos.financasapp.service.ResumoService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/v1/resumos-meses", produces = MediaType.APPLICATION_JSON_VALUE)
public class TransacaoResumoMesController {

    private ResumoService resumoService;

    @GetMapping
    public ResumoMesDto consultarResumoMes(@RequestParam Integer ano, @RequestParam Integer mes, @RequestParam Long carteiraId) {
        return resumoService.obterResumoMes(ano, mes, carteiraId);
    }
}
