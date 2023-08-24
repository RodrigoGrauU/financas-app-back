package dev.rodrigovasconcelos.financasapp.repository.impl;

import dev.rodrigovasconcelos.financasapp.dto.AnoTransacaoDto;
import dev.rodrigovasconcelos.financasapp.repository.CarteiraRepositoryQueries;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class CarteiraRepositoryImpl implements CarteiraRepositoryQueries {

    @PersistenceContext
    EntityManager entityManager;

    public Set<AnoTransacaoDto> find(Long... carteirasId) {
        Set<AnoTransacaoDto> listaAnoTransacaoDtos = new HashSet<>();
        for (Long carteiraId : carteirasId) {
            TypedQuery<Integer> query = entityManager.createQuery("SELECT DISTINCT EXTRACT(YEAR FROM t.dataCriacao) " +
                    "FROM transacoes t WHERE t.carteira.id = :idCarteira", Integer.class);
            query.setParameter("idCarteira", carteiraId);
            List<Integer> anosDisponiveis = query.getResultList();

            anosDisponiveis.forEach(ano -> {
                AnoTransacaoDto anoTransacaoDto = new AnoTransacaoDto();
                anoTransacaoDto.setAno(ano);

                TypedQuery<Integer> mesesDisponivesQuery = entityManager.createQuery("SELECT DISTINCT EXTRACT(MONTH FROM t.dataCriacao) FROM transacoes t " +
                        "WHERE t.carteira.id = :idCarteira AND EXTRACT(YEAR FROM t.dataCriacao) = :anoDisponivel", Integer.class);
                mesesDisponivesQuery.setParameter("idCarteira", carteiraId);
                mesesDisponivesQuery.setParameter("anoDisponivel", ano);

                List<Integer> mesesDisponiveis = mesesDisponivesQuery.getResultList();
                anoTransacaoDto.setMeses(new HashSet<>(mesesDisponiveis));

                listaAnoTransacaoDtos.add(anoTransacaoDto);
            });
        }

        return listaAnoTransacaoDtos;
    }
}
