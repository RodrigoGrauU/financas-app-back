package dev.rodrigovasconcelos.financasapp.mapper;

import dev.rodrigovasconcelos.financasapp.dto.CategoriaTransacaoDto;
import dev.rodrigovasconcelos.financasapp.entity.CategoriaTransacao;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CategoriaTransacaoMapper {
    CategoriaTransacaoMapper INSTANCE = Mappers.getMapper(CategoriaTransacaoMapper.class);

    CategoriaTransacaoDto categoriaTransacaoToCategoriaTransacaoDto(CategoriaTransacao categoriaTransacao);
    CategoriaTransacao categoriaTransacaoDtoToCategoriaTransacao(CategoriaTransacaoDto categoriaTransacaoDto);
}
