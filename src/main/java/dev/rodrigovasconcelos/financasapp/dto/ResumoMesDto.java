package dev.rodrigovasconcelos.financasapp.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResumoMesDto {
    private Integer ano;
    private Integer mes;
    private BigDecimal entrada;
    private BigDecimal saida;

    @Getter(AccessLevel.NONE)
    private BigDecimal saldoFinal;

    public BigDecimal getSaldoFinal() {
        return this.entrada.subtract(this.saida);
    }
}
