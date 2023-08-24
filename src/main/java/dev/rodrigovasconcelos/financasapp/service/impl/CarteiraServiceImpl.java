package dev.rodrigovasconcelos.financasapp.service.impl;

import dev.rodrigovasconcelos.financasapp.dto.AnoTransacaoDto;
import dev.rodrigovasconcelos.financasapp.dto.CarteiraComMesesAnoDto;
import dev.rodrigovasconcelos.financasapp.dto.CarteiraDto;
import dev.rodrigovasconcelos.financasapp.entity.Carteira;
import dev.rodrigovasconcelos.financasapp.mapper.CarteiraMapper;
import dev.rodrigovasconcelos.financasapp.repository.CarteiraRepository;
import dev.rodrigovasconcelos.financasapp.service.CarteiraService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class CarteiraServiceImpl implements CarteiraService {

    private CarteiraRepository carteiraRepository;

    @Override
    @Transactional
    public CarteiraDto salvarCarteira(CarteiraDto carteiraDto) {
        Carteira carteira = CarteiraMapper.INSTANCE.CarteiraDtoToCarteira(carteiraDto);
        // TODO - REMOVER MOCK USER
        if(carteira != null) {
            Long userId = 1L;
            carteira.setUserId(userId);
            Carteira carteiraSalva = carteiraRepository.save(carteira);
            return CarteiraMapper.INSTANCE.carteiraToCarteiraDto(carteiraSalva);
        }
        return null;
    }

    @Override
    public Set<CarteiraComMesesAnoDto> listaCarteiras(Long usuarioId) {
        Set<Carteira> carteiras = carteiraRepository.findByUserId(usuarioId).orElse(new HashSet<>());
        Set<CarteiraComMesesAnoDto> carteirasDto = new HashSet<>();
        carteiras.forEach(carteira -> carteirasDto.add(CarteiraMapper.INSTANCE.carteiraToCarteiraComMesesAnoDto(carteira)));

        carteirasDto.forEach(carteiraDto -> {
            Set<AnoTransacaoDto> listaAnoTransacao = carteiraRepository.find(carteiraDto.getId());
            carteiraDto.setListaAnosTransacoes(listaAnoTransacao);
        });

        return carteirasDto;
    }

    @Override
    @Transactional
    public void removerCarteira(Long carteiraId) {
        carteiraRepository.deleteById(carteiraId);
        carteiraRepository.flush();
    }

    @Override
    public Carteira buscarOuFalhar(Long carteiraId) {
        return carteiraRepository.findById(carteiraId).orElseThrow(RuntimeException::new);
    }
}
