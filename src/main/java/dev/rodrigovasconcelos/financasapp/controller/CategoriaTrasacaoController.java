package dev.rodrigovasconcelos.financasapp.controller;

import dev.rodrigovasconcelos.financasapp.dto.CategoriaTransacaoDto;
import dev.rodrigovasconcelos.financasapp.service.CategoriaTransacaoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("v1/categorias-transacoes")
public class CategoriaTrasacaoController {

    private CategoriaTransacaoService categoriaTransacaoService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<CategoriaTransacaoDto> listar() {
        Long usuarioId = 1L;
        return categoriaTransacaoService.listar(usuarioId);
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public CategoriaTransacaoDto adicionar(@RequestBody CategoriaTransacaoDto categoriaTransacaoDto) {
        return categoriaTransacaoService.adicionar(categoriaTransacaoDto);
    }

    @PutMapping("/{categoriaTransacaoId}")
    public CategoriaTransacaoDto atualizar(@PathVariable Long categoriaTransacaoId, @RequestBody CategoriaTransacaoDto categoriaTransacaoDto) {
        return categoriaTransacaoService.atualizar(categoriaTransacaoId, categoriaTransacaoDto);
    }

    @DeleteMapping("/{categoriaTransacaoId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long categoriaTransacaoId) {
        categoriaTransacaoService.remover(categoriaTransacaoId);
    }
}
