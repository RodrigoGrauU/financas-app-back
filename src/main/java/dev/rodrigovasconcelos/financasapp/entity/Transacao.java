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

    private String descricao;

    @CreationTimestamp
    private OffsetDateTime dataCriacao;

    @Enumerated(EnumType.STRING)
    private TipoTransacao tipoTransacao;

    @JoinColumn
    @ManyToOne
    private CategoriaTransacao categoriaTransacao;

}
