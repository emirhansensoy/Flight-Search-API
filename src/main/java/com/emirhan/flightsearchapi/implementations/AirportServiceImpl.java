package com.emirhan.flightsearchapi.implementations;

import com.emirhan.flightsearchapi.models.Airport;
import com.emirhan.flightsearchapi.repositories.IAirportRepository;
import com.emirhan.flightsearchapi.services.IAirportService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AirportServiceImpl implements IAirportService {

    private IAirportRepository airportRepository;

    public AirportServiceImpl(IAirportRepository airportRepository) {
        this.airportRepository = airportRepository;
    }

    @Override
    public Airport createAirport(Airport airport) {
        return airportRepository.save(airport);
    }

    @Override
    public Airport updateAirport(long id, Airport updatedAirport) {
        updatedAirport.setId(id);
        return airportRepository.save(updatedAirport);
    }

    @Override
    public void deleteAirport(long id) {
        this.airportRepository.deleteById(id);
    }

    @Override
    public Airport getAirport(long id) {
        return airportRepository.findById(id).get();
    }

    @Override
    public Airport getAirportByCity(String city) {
        return airportRepository.findByCity(city).get();
    }

    @Override
    public List<Airport> getAllAirports() {
        return airportRepository.findAll();
    }
}
