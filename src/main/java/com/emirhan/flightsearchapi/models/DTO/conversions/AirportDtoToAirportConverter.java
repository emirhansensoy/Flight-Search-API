package com.emirhan.flightsearchapi.models.DTO.conversions;

import com.emirhan.flightsearchapi.models.Airport;
import com.emirhan.flightsearchapi.models.DTO.AirportDTO;
import org.springframework.stereotype.Component;
import org.springframework.core.convert.converter.Converter;

@Component
public class AirportDtoToAirportConverter implements Converter<AirportDTO, Airport> {

    @Override
    public Airport convert(AirportDTO source) {
        Airport airport = new Airport();
        airport.setId(0);
        airport.setCity(source.getCity());
        return airport;
    }
}