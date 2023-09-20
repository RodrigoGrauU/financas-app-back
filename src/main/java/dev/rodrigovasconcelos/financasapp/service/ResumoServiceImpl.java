package dev.rodrigovasconcelos.financasapp.service;

import dev.rodrigovasconcelos.financasapp.dto.ResumoMesDto;
import dev.rodrigovasconcelos.financasapp.entity.Transacao;
import dev.rodrigovasconcelos.financasapp.repository.TransacaoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.LinkedHashSet;

@AllArgsConstructor
@Service
public class ResumoServiceImpl implements ResumoService {

    private TransacaoRepository transacaoRepository;

    @Override
    public ResumoMesDto obterResumoMes(Integer ano, Integer mes, Long carteiraId) {
        ResumoMesDto resumoMesDto = new ResumoMesDto();
        resumoMesDto.setMes(mes);
        resumoMesDto.setAno(ano);

        LinkedHashSet<Transacao> transacoesCredito = transacaoRepository.buscaTransacoesCreditoPorMesAnoCarteira(carteiraId, ano, mes);
        BigDecimal totalCredito  = transacoesCredito.stream().map(Transacao::getValor).reduce(BigDecimal.ZERO, BigDecimal::add);
        resumoMesDto.setEntrada(totalCredito);

        LinkedHashSet<Transacao> transacoesDebito = transacaoRepository.buscaTransacoesDebitoPorMesAnoCarteira(carteiraId, ano, mes);
        BigDecimal totalDebito = transacoesDebito.stream().map(Transacao::getValor).reduce(BigDecimal.ZERO, BigDecimal::add);
        resumoMesDto.setSaida(totalDebito);

        return resumoMesDto;
    }
}
