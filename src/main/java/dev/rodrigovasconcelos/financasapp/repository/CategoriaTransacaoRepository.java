package dev.rodrigovasconcelos.financasapp.repository;

import dev.rodrigovasconcelos.financasapp.entity.CategoriaTransacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface CategoriaTransacaoRepository extends JpaRepository<CategoriaTransacao, Long> {
    Set<CategoriaTransacao> findByUsuarioId(Long usuarioId);
}
