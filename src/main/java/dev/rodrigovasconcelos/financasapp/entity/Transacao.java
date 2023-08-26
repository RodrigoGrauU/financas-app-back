package dev.rodrigovasconcelos.financasapp.entity;

import dev.rodrigovasconcelos.financasapp.entity.Carteira;
import dev.rodrigovasconcelos.financasapp.entity.CategoriaTransacao;
import dev.rodrigovasconcelos.financasapp.entity.TipoTransacao;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "transacoes")
public class Transacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn
    @ManyToOne(optional = false)
    private Carteira carteira;

    private BigDecimal valor;

    private String descricao;

    @CreationTimestamp
    private OffsetDateTime dataCriacao;

    @UpdateTimestamp
    private OffsetDateTime dataAlteracao;

    private LocalDate dataTransacao;

    @Enumerated(EnumType.STRING)
    private TipoTransacao tipoTransacao;

    @JoinColumn
    @ManyToOne
    private CategoriaTransacao categoriaTransacao;

}
