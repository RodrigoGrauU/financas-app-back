package dev.rodrigovasconcelos.financasapp.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CarteiraDto {
    private Long id;

    @NotBlank
    private String nome;

    private String descricao;
}
