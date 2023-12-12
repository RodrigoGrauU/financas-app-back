package dev.rodrigovasconcelos.financasapp.controller;

import dev.rodrigovasconcelos.financasapp.dto.CarteiraComMesesAnoDto;
import dev.rodrigovasconcelos.financasapp.dto.CarteiraDto;
import dev.rodrigovasconcelos.financasapp.entity.Carteira;
import dev.rodrigovasconcelos.financasapp.mapper.CarteiraMapper;
import dev.rodrigovasconcelos.financasapp.service.CarteiraService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
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
@RequestMapping("/v1/carteiras")
@AllArgsConstructor
public class CarteiraController {

    private CarteiraService carteiraService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<CarteiraComMesesAnoDto> listaCarteiras() {
        return carteiraService.listaCarteiras(getUsername());
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public CarteiraDto salvarCarteira(@Valid @RequestBody CarteiraDto carteiraDto) {
        return carteiraService.salvarCarteira(carteiraDto, getUsername());
    }

    @DeleteMapping("/{carteiraId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void removerCarteira(@PathVariable Long carteiraId) {
        carteiraService.removerCarteira(carteiraId);
    }

    @PutMapping("/{carteiraId}")
    public CarteiraDto atualizarCarteira(@PathVariable Long carteiraId, @Valid @RequestBody CarteiraDto carteiraDto) {
        Carteira carteiraAtual = carteiraService.buscarOuFalhar(carteiraId);
        Carteira carteira = CarteiraMapper.INSTANCE.CarteiraDtoToCarteira(carteiraDto);

        BeanUtils.copyProperties(carteira, carteiraAtual, "id");
        return carteiraService.salvarCarteira(CarteiraMapper.INSTANCE.carteiraToCarteiraDto(carteiraAtual), getUsername());
    }

    private String getUsername() {
        SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();
        return securityContextHolderStrategy.getContext().getAuthentication().getName();
    }
}
