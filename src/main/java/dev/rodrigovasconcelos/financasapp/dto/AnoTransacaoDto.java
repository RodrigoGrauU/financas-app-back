package dev.rodrigovasconcelos.financasapp.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class AnoTransacaoDto {
    private int ano;
    private Set<Integer> meses;
}
