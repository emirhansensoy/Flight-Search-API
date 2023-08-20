package com.emirhan.flightsearchapi.repositories;

import com.emirhan.flightsearchapi.models.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IFlightRepository extends JpaRepository<Flight, Long>, JpaSpecificationExecutor<Flight> {
}
