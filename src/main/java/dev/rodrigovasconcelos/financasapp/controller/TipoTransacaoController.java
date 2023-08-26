package dev.rodrigovasconcelos.financasapp.controller;


import dev.rodrigovasconcelos.financasapp.entity.TipoTransacao;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/tipos-transacoes")
public class TipoTransacaoController {

    @GetMapping
    public TipoTransacao[] listar() {
        return TipoTransacao.listaTipos();
    }
}
