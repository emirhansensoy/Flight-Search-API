package com.emirhan.flightsearchapi.services;

import com.emirhan.flightsearchapi.models.Airport;

import java.util.List;

public interface IAirportService {
    Airport createAirport(Airport airport);

    Airport updateAirport(long id, Airport airport);

    void deleteAirport(long id);

    Airport getAirport(long id);

    List<Airport> getAllAirports();

    Airport getAirportByCity(String city);
}
