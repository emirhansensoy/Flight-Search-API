package com.emirhan.flightsearchapi.models.DTO.conversions;

import com.emirhan.flightsearchapi.models.DTO.FlightDTO;
import com.emirhan.flightsearchapi.models.Flight;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class FlightDtoToFlightConverter implements Converter<FlightDTO, Flight> {

    private final AirportDtoToAirportConverter airportDtoToAirportConverter;

    public FlightDtoToFlightConverter(AirportDtoToAirportConverter airportDtoToAirportConverter) {
        this.airportDtoToAirportConverter = airportDtoToAirportConverter;
    }

    @Override
    public Flight convert(FlightDTO source) {
        Flight flight = new Flight();
        flight.setDepartureAirport(this.airportDtoToAirportConverter.convert(source.getDepartureAirport()));
        flight.setArrivalAirport(this.airportDtoToAirportConverter.convert(source.getArrivalAirport()));
        flight.setDepartureDateTime(source.getDepartureDateTime());
        flight.setReturnDateTime(source.getReturnDateTime());
        return flight;
    }

}