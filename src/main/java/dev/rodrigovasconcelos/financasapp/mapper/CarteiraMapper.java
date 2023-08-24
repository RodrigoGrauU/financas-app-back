package dev.rodrigovasconcelos.financasapp.mapper;

import dev.rodrigovasconcelos.financasapp.dto.CarteiraComMesesAnoDto;
import dev.rodrigovasconcelos.financasapp.dto.CarteiraDto;
import dev.rodrigovasconcelos.financasapp.entity.Carteira;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CarteiraMapper {
    CarteiraMapper INSTANCE = Mappers.getMapper(CarteiraMapper.class);

    CarteiraComMesesAnoDto carteiraToCarteiraComMesesAnoDto(Carteira carteira);

    CarteiraDto carteiraToCarteiraDto(Carteira carteira);

    Carteira CarteiraDtoToCarteira(CarteiraDto carteiraDto);
}
