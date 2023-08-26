package dev.rodrigovasconcelos.financasapp.repository;

import dev.rodrigovasconcelos.financasapp.entity.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {

    List<Transacao> buscarTransacoes(Long carteiraId, int anoTransacao, int mesTransacao);
}
