package dev.rodrigovasconcelos.financasapp.controller;

import dev.rodrigovasconcelos.financasapp.dto.CarteiraComMesesAnoDto;
import dev.rodrigovasconcelos.financasapp.dto.CarteiraDto;
import dev.rodrigovasconcelos.financasapp.entity.Carteira;
import dev.rodrigovasconcelos.financasapp.mapper.CarteiraMapper;
import dev.rodrigovasconcelos.financasapp.service.CarteiraService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/v1/carteiras")
@AllArgsConstructor
public class CarteiraController {

    private CarteiraService carteiraService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<CarteiraComMesesAnoDto> listaCarteiras() {
        Long idUsuarioMock = 1L;
        return carteiraService.listaCarteiras(idUsuarioMock);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public CarteiraDto salvarCarteira(@RequestBody CarteiraDto carteiraDto) {
        return carteiraService.salvarCarteira(carteiraDto);
    }

    @DeleteMapping("/{carteiraId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void removerCarteira(@PathVariable Long carteiraId) {
        carteiraService.removerCarteira(carteiraId);
    }

    @PutMapping("/{carteiraId}")
    public CarteiraDto atualizarCarteira(@PathVariable Long carteiraId, @RequestBody CarteiraDto carteiraDto) {
        Carteira carteiraAtual = carteiraService.buscarOuFalhar(carteiraId);
        Carteira carteira = CarteiraMapper.INSTANCE.CarteiraDtoToCarteira(carteiraDto);

        BeanUtils.copyProperties(carteira, carteiraAtual, "id");
        return carteiraService.salvarCarteira(CarteiraMapper.INSTANCE.carteiraToCarteiraDto(carteiraAtual));
    }
}
