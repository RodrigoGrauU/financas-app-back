package dev.rodrigovasconcelos.financasapp.repository.impl;

import dev.rodrigovasconcelos.financasapp.entity.Transacao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class TransacaoRepositoryImpl {

    @PersistenceContext
    EntityManager entityManager;

    public List<Transacao> buscarTransacoes(Long carteiraId, int anoTransacao, int mesTransacao) {
        var stringQuery = "SELECT t FROM transacoes t WHERE t.carteira.id = :carteiraId ";
        Map<String, Object> listaParametros = new HashMap<>();
        listaParametros.put("carteiraId", carteiraId);

        if(anoTransacao != 0) {
            stringQuery += "AND EXTRACT(YEAR FROM t.dataTransacao) = :anoTransacao ";
            listaParametros.put("anoTransacao", anoTransacao);
        }

        if(mesTransacao != 0) {
            stringQuery += "AND EXTRACT(MONTH FROM t.dataTransacao) = :mesTransacao ";
            listaParametros.put("mesTransacao", mesTransacao);
        }

        stringQuery += "ORDER BY t.dataTransacao ";
        TypedQuery<Transacao> queryFinal =  entityManager.createQuery(stringQuery, Transacao.class);
        listaParametros.forEach(queryFinal::setParameter);
        return queryFinal.getResultList();
    }
}
