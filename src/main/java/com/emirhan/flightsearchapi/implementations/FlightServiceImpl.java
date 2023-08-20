package com.emirhan.flightsearchapi.implementations;

import com.emirhan.flightsearchapi.models.DTO.FlightDTO;
import com.emirhan.flightsearchapi.models.Flight;
import com.emirhan.flightsearchapi.repositories.IFlightRepository;
import com.emirhan.flightsearchapi.services.IFlightService;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlightServiceImpl implements IFlightService {

    private IFlightRepository flightRepository;

    FlightServiceImpl(IFlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    @Override
    public Flight createFlight(Flight flight) {
        return this.flightRepository.save(flight);
    }

    @Override
    public Flight updateFlight(long id, Flight updatedFlight) {
        updatedFlight.setId(id);
        return this.flightRepository.save(updatedFlight);
    }

    @Override
    public void deleteFlight(long id) {
        flightRepository.deleteById(id);
    }

    @Override
    public Flight getFlight(long id) {
        return flightRepository.findById(id).get();
    }

    @Override
    public List<Flight> getAllFlights() {
        return flightRepository.findAll();
    }

    @Override
    public List<Flight> searchFlights(Specification<Flight> specification) {
        return flightRepository.findAll(specification);
    }
}
