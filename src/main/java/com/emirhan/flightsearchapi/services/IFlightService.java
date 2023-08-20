package com.emirhan.flightsearchapi.services;

import com.emirhan.flightsearchapi.models.DTO.FlightDTO;
import com.emirhan.flightsearchapi.models.Flight;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface IFlightService {
    Flight createFlight(Flight flight);

    Flight updateFlight(long id, Flight flight);

    void deleteFlight(long id);

    Flight getFlight(long id);

    List<Flight> getAllFlights();

    List<Flight> searchFlights(Specification<Flight> specification);
}
