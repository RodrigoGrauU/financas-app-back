package dev.rodrigovasconcelos.financasapp.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoriaTransacaoDto {
    private Long id;
    private String nome;
    private String descricao;
}
