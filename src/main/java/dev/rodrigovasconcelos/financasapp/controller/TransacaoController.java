package dev.rodrigovasconcelos.financasapp.controller;

import dev.rodrigovasconcelos.financasapp.dto.TransacaoDto;
import dev.rodrigovasconcelos.financasapp.dto.TransacaoSalvarDto;
import dev.rodrigovasconcelos.financasapp.service.TransacaoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/v1/carteiras/{carteiraId}/transacoes", produces = MediaType.APPLICATION_JSON_VALUE)
public class TransacaoController {

    private TransacaoService transacaoService;

    @GetMapping
    public Set<TransacaoDto> listar(@PathVariable Long carteiraId, @RequestParam(required = false, defaultValue = "0") int anoTransacao,
                                    @RequestParam(required = false, defaultValue = "0") int mesTransacao) {
        return transacaoService.listar(carteiraId,  anoTransacao, mesTransacao);
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public TransacaoDto salvar(@RequestBody TransacaoSalvarDto transacaoSalvarDto) {
        return transacaoService.salvar(transacaoSalvarDto);
    }

    @DeleteMapping("/{transacaoId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long transacaoId) {
        transacaoService.remover(transacaoId);

    }

    @PutMapping("/{transacaoId}")
    public TransacaoDto atualizar(@PathVariable Long transacaoId, @RequestBody TransacaoSalvarDto transacaoSalvarDto) {
        return transacaoService.atualizar(transacaoId, transacaoSalvarDto);
    }
}
