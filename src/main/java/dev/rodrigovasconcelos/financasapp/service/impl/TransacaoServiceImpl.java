package dev.rodrigovasconcelos.financasapp.service.impl;

import dev.rodrigovasconcelos.financasapp.dto.TransacaoDto;
import dev.rodrigovasconcelos.financasapp.dto.TransacaoSalvarDto;
import dev.rodrigovasconcelos.financasapp.entity.Carteira;
import dev.rodrigovasconcelos.financasapp.entity.CategoriaTransacao;
import dev.rodrigovasconcelos.financasapp.entity.Transacao;
import dev.rodrigovasconcelos.financasapp.mapper.TransacaoMapper;
import dev.rodrigovasconcelos.financasapp.repository.TransacaoRepository;
import dev.rodrigovasconcelos.financasapp.service.CarteiraService;
import dev.rodrigovasconcelos.financasapp.service.CategoriaTransacaoService;
import dev.rodrigovasconcelos.financasapp.service.TransacaoService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Service
public class TransacaoServiceImpl implements TransacaoService {

    private TransacaoRepository transacaoRepository;
    private CarteiraService carteiraService;
    private CategoriaTransacaoService categoriaService;

    @Override
    public Set<TransacaoDto> listar(Long carteiraId, int anoTransacao, int mesTransacao) {
        Set<TransacaoDto> transacaoDtos = new LinkedHashSet<>();
        transacaoRepository.buscarTransacoes(carteiraId, anoTransacao, mesTransacao).forEach(transacao -> {
            transacaoDtos.add(TransacaoMapper.INSTANCE.transacaoToTransacaoDto(transacao));
        });
        return transacaoDtos;
    }

    @Transactional
    @Override
    public TransacaoDto salvar(TransacaoSalvarDto transacaoSalvarDto) {
        Transacao transacao = TransacaoMapper.INSTANCE.transacaoSalvarDtoToTransacao(transacaoSalvarDto);
        CategoriaTransacao categoriaTransacao = categoriaService.buscarOuFalhar(transacaoSalvarDto.getCategoriaTransacao().getId());
        Carteira carteira = carteiraService.buscarOuFalhar(transacao.getCarteira().getId());
        transacao.setCarteira(carteira);
        transacao.setCategoriaTransacao(categoriaTransacao);
        return TransacaoMapper.INSTANCE.transacaoToTransacaoDto(transacaoRepository.save(transacao));
    }

    @Transactional
    @Override
    public void remover(Long transacaoId) {
        Transacao transacao = this.buscarOuFalhar(transacaoId);
        transacaoRepository.delete(transacao);
    }

    @Transactional
    @Override
    public TransacaoDto atualizar(Long transacaoId, TransacaoSalvarDto transacaoSalvarDto) {
        Transacao transacaoAtual = this.buscarOuFalhar(transacaoId);
        Transacao transacaoAtualizada = TransacaoMapper.INSTANCE.transacaoSalvarDtoToTransacao(transacaoSalvarDto);
        BeanUtils.copyProperties(transacaoAtualizada, transacaoAtual, "id", "dataCriacao");
        Carteira carteira = carteiraService.buscarOuFalhar(transacaoSalvarDto.getCarteira().getId());
        CategoriaTransacao categoriaTransacao = categoriaService.buscarOuFalhar(transacaoSalvarDto.getCategoriaTransacao().getId());
        transacaoAtual.setCarteira(carteira);
        transacaoAtual.setCategoriaTransacao(categoriaTransacao);
        return TransacaoMapper.INSTANCE.transacaoToTransacaoDto(transacaoRepository.save(transacaoAtual));
    }

    @Transactional
    @Override
    public void salvar(List<TransacaoSalvarDto> transacoes) {
        List<Transacao> listaTransacoesEntity = transacoes.stream()
                .map(transacao -> TransacaoMapper.INSTANCE.transacaoSalvarDtoToTransacao(transacao))
                .toList();
        transacaoRepository.saveAll(listaTransacoesEntity);
    }

    private Transacao buscarOuFalhar(Long transacaoId) {
        return transacaoRepository.findById(transacaoId).orElseThrow(RuntimeException::new);
    }
}
