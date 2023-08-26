package dev.rodrigovasconcelos.financasapp.repository;

import dev.rodrigovasconcelos.financasapp.entity.Carteira;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface CarteiraRepository extends JpaRepository<Carteira, Long>, CarteiraRepositoryQueries {
    Optional<Set<Carteira>> findByUserId(Long User);
}
