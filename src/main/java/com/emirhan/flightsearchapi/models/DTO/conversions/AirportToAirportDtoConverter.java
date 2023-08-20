package com.emirhan.flightsearchapi.models.DTO.conversions;

import com.emirhan.flightsearchapi.models.Airport;
import com.emirhan.flightsearchapi.models.DTO.AirportDTO;
import org.springframework.stereotype.Component;
import org.springframework.core.convert.converter.Converter;

@Component
public class AirportToAirportDtoConverter implements Converter<Airport, AirportDTO> {

    @Override
    public AirportDTO convert(Airport source) {
        return new AirportDTO(source.getCity());
    }

}