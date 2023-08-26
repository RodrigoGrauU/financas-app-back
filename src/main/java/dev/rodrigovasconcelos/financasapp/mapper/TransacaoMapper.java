package dev.rodrigovasconcelos.financasapp.mapper;

import dev.rodrigovasconcelos.financasapp.dto.CategoriaTransacaoDto;
import dev.rodrigovasconcelos.financasapp.dto.TransacaoDto;
import dev.rodrigovasconcelos.financasapp.dto.TransacaoSalvarDto;
import dev.rodrigovasconcelos.financasapp.entity.CategoriaTransacao;
import dev.rodrigovasconcelos.financasapp.entity.Transacao;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TransacaoMapper {
    TransacaoMapper INSTANCE = Mappers.getMapper(TransacaoMapper.class);

    @Mapping(source = "categoriaTransacao", target = "categoriaTransacao")
    TransacaoDto transacaoToTransacaoDto(Transacao transacao);

    @Mapping(source = "categoriaTransacao", target = "categoriaTransacao")
    Transacao transacaoDtoToTransacao(TransacaoDto transacaoDto);

    Transacao transacaoSalvarDtoToTransacao(TransacaoSalvarDto transacaoSalvarDto);

    CategoriaTransacao categoriaTransacao(CategoriaTransacaoDto categoriaTransacao);

    CategoriaTransacaoDto categoriaTransacaoTocategoriaTransacaoDto(CategoriaTransacao categoriaTransacao);
}
