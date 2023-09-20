package dev.rodrigovasconcelos.financasapp.repository;

import dev.rodrigovasconcelos.financasapp.entity.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.LinkedHashSet;
import java.util.List;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {

    List<Transacao> buscarTransacoes(Long carteiraId, int anoTransacao, int mesTransacao);

    @Query("SELECT t FROM transacoes t WHERE t.tipoTransacao='CREDITO' AND YEAR(t.dataTransacao) = :anoTransacao AND MONTH(t.dataTransacao) = :mesTransacao and t.carteira.id = :carteiraId " )
    LinkedHashSet<Transacao> buscaTransacoesCreditoPorMesAnoCarteira(@Param("carteiraId") Long usuarioId, @Param("anoTransacao") Integer ano, @Param("mesTransacao") Integer mes);

    @Query("SELECT t FROM transacoes t WHERE t.tipoTransacao='DEBITO' AND YEAR(t.dataTransacao) = :anoTransacao AND MONTH(t.dataTransacao) = :mesTransacao and t.carteira.id = :carteiraId " )
    LinkedHashSet<Transacao> buscaTransacoesDebitoPorMesAnoCarteira(@Param("carteiraId") Long usuarioId, @Param("anoTransacao") Integer ano, @Param("mesTransacao") Integer mes);
}
