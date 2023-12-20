package dev.rodrigovasconcelos.financasapp.controller;

import dev.rodrigovasconcelos.financasapp.dto.CategoriaTransacaoDto;
import dev.rodrigovasconcelos.financasapp.entity.Usuario;
import dev.rodrigovasconcelos.financasapp.service.CategoriaTransacaoService;
import dev.rodrigovasconcelos.financasapp.service.impl.UsuarioServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("v1/categorias-transacoes")
public class CategoriaTrasacaoController {

    private CategoriaTransacaoService categoriaTransacaoService;
    private UsuarioServiceImpl usuarioService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<CategoriaTransacaoDto> listar() {
        Usuario usuario = usuarioService.findUserByContext();
        return categoriaTransacaoService.listar(usuario.getId());
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public CategoriaTransacaoDto adicionar(@RequestBody CategoriaTransacaoDto categoriaTransacaoDto) {
        return categoriaTransacaoService.adicionar(categoriaTransacaoDto, usuarioService.getUserId());
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
