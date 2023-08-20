package com.emirhan.flightsearchapi.repositories;

import com.emirhan.flightsearchapi.models.Airport;
import com.emirhan.flightsearchapi.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IAirportRepository extends JpaRepository<Airport, Long> {
    Optional<Airport> findByCity(String city);
}
