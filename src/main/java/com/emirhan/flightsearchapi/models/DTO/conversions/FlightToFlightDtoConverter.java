package com.emirhan.flightsearchapi.models.DTO.conversions;

import com.emirhan.flightsearchapi.models.Flight;
import com.emirhan.flightsearchapi.models.DTO.FlightDTO;
import org.springframework.stereotype.Component;
import org.springframework.core.convert.converter.Converter;

@Component
public class FlightToFlightDtoConverter implements Converter<Flight, FlightDTO> {

    private final AirportToAirportDtoConverter airportToAirportDtoConverter;

    public FlightToFlightDtoConverter(AirportToAirportDtoConverter airportToAirportDtoConverter) {
        this.airportToAirportDtoConverter = airportToAirportDtoConverter;
    }

    @Override
    public FlightDTO convert(Flight source) {
        FlightDTO flightDto = new FlightDTO(
                source.getDepartureAirport() != null
                        ? this.airportToAirportDtoConverter.convert(source.getDepartureAirport())
                        : null,
                source.getArrivalAirport() != null
                        ? this.airportToAirportDtoConverter.convert(source.getArrivalAirport())
                        : null,
                source.getDepartureDateTime(),
                source.getReturnDateTime());
        return flightDto;
    }

}