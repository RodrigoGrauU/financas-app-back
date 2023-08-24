package dev.rodrigovasconcelos.financasapp.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class CarteiraComMesesAnoDto {
    private Long id;
    private String nome;
    private String descricao;
    private Set<AnoTransacaoDto> listaAnosTransacoes = new HashSet<>();
}
