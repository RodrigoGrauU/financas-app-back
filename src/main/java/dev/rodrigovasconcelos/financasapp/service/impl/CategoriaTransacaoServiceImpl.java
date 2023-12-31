package dev.rodrigovasconcelos.financasapp.service.impl;

import dev.rodrigovasconcelos.financasapp.dto.CategoriaTransacaoDto;
import dev.rodrigovasconcelos.financasapp.entity.CategoriaTransacao;
import dev.rodrigovasconcelos.financasapp.mapper.CategoriaTransacaoMapper;
import dev.rodrigovasconcelos.financasapp.repository.CategoriaTransacaoRepository;
import dev.rodrigovasconcelos.financasapp.service.CategoriaTransacaoService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class CategoriaTransacaoServiceImpl implements CategoriaTransacaoService {

    private CategoriaTransacaoRepository categoriaTransacaoRepository;

    @Override
    public Set<CategoriaTransacaoDto> listar(Long usuarioId) {
        Set<CategoriaTransacao> categorias = categoriaTransacaoRepository.findByUsuarioId(usuarioId);
        Set<CategoriaTransacaoDto> categoriasDto = new LinkedHashSet<>();
        categorias.forEach(categoria -> categoriasDto.add(CategoriaTransacaoMapper.INSTANCE
                .categoriaTransacaoToCategoriaTransacaoDto(categoria)));

        return categoriasDto;
    }

    @Override
    public CategoriaTransacaoDto adicionar(CategoriaTransacaoDto categoriaTransacaoDto, Long usuarioId) {
        CategoriaTransacao categoriaTransacao = CategoriaTransacaoMapper.INSTANCE.categoriaTransacaoDtoToCategoriaTransacao(categoriaTransacaoDto);
        categoriaTransacao.setUsuarioId(usuarioId);
        return CategoriaTransacaoMapper.INSTANCE.categoriaTransacaoToCategoriaTransacaoDto(categoriaTransacaoRepository.save(categoriaTransacao));
    }

    @Override
    public CategoriaTransacaoDto atualizar(Long categoriaTransacaoId, CategoriaTransacaoDto categoriaTransacaoDto) {
        CategoriaTransacao categoriaTransacaoAtual = this.buscarOuFalhar(categoriaTransacaoId);
        CategoriaTransacao categoriaTransacao = CategoriaTransacaoMapper.INSTANCE.categoriaTransacaoDtoToCategoriaTransacao(categoriaTransacaoDto);
        BeanUtils.copyProperties(categoriaTransacao, categoriaTransacaoAtual, "id", "usuarioId");
        return CategoriaTransacaoMapper.INSTANCE.categoriaTransacaoToCategoriaTransacaoDto(categoriaTransacaoRepository.save(categoriaTransacaoAtual));
    }

    @Override
    public void remover(Long categoriaTransacaoId) {
        categoriaTransacaoRepository.deleteById(categoriaTransacaoId);
    }

    public CategoriaTransacao buscarOuFalhar(Long categoriaTransacaoId) {
        return categoriaTransacaoRepository.findById(categoriaTransacaoId).orElseThrow(RuntimeException::new);
    }

}
