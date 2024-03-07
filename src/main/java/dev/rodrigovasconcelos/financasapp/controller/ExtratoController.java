package dev.rodrigovasconcelos.financasapp.controller;

import dev.rodrigovasconcelos.financasapp.dto.TransacaoDto;
import dev.rodrigovasconcelos.financasapp.dto.TransacaoSalvarDto;
import dev.rodrigovasconcelos.financasapp.service.BankStatementExtractorService;
import dev.rodrigovasconcelos.financasapp.service.TransacaoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/v1/extrato")
public class ExtratoController {

    private final BankStatementExtractorService extractorService;
    private final TransacaoService transacaoService;

    public ExtratoController(BankStatementExtractorService extractorService, TransacaoService transacaoService) {
        this.extractorService = extractorService;
        this.transacaoService = transacaoService;
    }

    @PostMapping(consumes = "multipart/form-data")
    public List<TransacaoDto> importExtratoBancario(@RequestParam("bankStatement") MultipartFile bankStatement) throws IOException {
        return extractorService.transactionsFromPdf(bankStatement);
    }

    @PostMapping("/salvarLote")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void salvar(@RequestBody List<TransacaoSalvarDto> transacoes) {
        transacaoService.salvar(transacoes);
    }
}
