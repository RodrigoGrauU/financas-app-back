package dev.rodrigovasconcelos.financasapp.dto;

import dev.rodrigovasconcelos.financasapp.entity.TipoTransacao;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TransacaoSalvarDto {
    private Long id;
    private CarteiraDto carteira;
    private BigDecimal valor;
    private String descricao;
    private LocalDate dataTransacao;
    private TipoTransacao tipoTransacao;
    private CategoriaTransacaoDto categoriaTransacao;
}
