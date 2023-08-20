package com.emirhan.flightsearchapi.controllers;

import com.emirhan.flightsearchapi.models.Airport;
import com.emirhan.flightsearchapi.models.DTO.AirportDTO;
import com.emirhan.flightsearchapi.models.DTO.FlightDTO;
import com.emirhan.flightsearchapi.models.DTO.conversions.FlightDtoToFlightConverter;
import com.emirhan.flightsearchapi.models.DTO.conversions.FlightToFlightDtoConverter;
import com.emirhan.flightsearchapi.models.Flight;
import com.emirhan.flightsearchapi.services.IAirportService;
import com.emirhan.flightsearchapi.services.IFlightService;
import com.emirhan.flightsearchapi.system.Result;
import com.emirhan.flightsearchapi.system.StatusCode;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/flight")
@SecurityRequirement(name = "BasicAuth")
public class FlightController {

    IFlightService flightService;
    IAirportService airportService;

    FlightDtoToFlightConverter flightDtoToFlightConverter;

    FlightToFlightDtoConverter flightToFlightDtoConverter;

    public FlightController(IFlightService flightService,
                            IAirportService airportService,
                            FlightDtoToFlightConverter flightDtoToFlightConverter,
                            FlightToFlightDtoConverter flightToFlightDtoConverter) {
        this.flightService = flightService;
        this.airportService = airportService;
        this.flightDtoToFlightConverter = flightDtoToFlightConverter;
        this.flightToFlightDtoConverter = flightToFlightDtoConverter;
    }

    @GetMapping("/{id}")
    public Result getFlightDetails(@PathVariable("id") long id) {
        try {
            Flight flight = this.flightService.getFlight(id);
            FlightDTO flightDTO = this.flightToFlightDtoConverter.convert(flight);
            return new Result(true, StatusCode.SUCCESS, "Find One Success", flightDTO);
        } catch (Exception e) {
            return null;
        }
    }

    @GetMapping()
    public Result getAllFlightDetails() {
        try {
            List<Flight> foundFlights = this.flightService.getAllFlights();
            List<FlightDTO> flightDTOs = foundFlights.stream()
                    .map(this.flightToFlightDtoConverter::convert)
                    .collect(Collectors.toList());
            return new Result(true, StatusCode.SUCCESS, "Find All Success", flightDTOs);
        } catch (Exception e) {
            return null;
        }
    }

    @PostMapping()
    public Result createFlightDetails(@RequestBody FlightDTO flightDTO) {
        try {
            Airport departureAirport = airportService.getAirportByCity(flightDTO.getDepartureAirport().getCity());
            Airport arrivalAirport = airportService.getAirportByCity(flightDTO.getArrivalAirport().getCity());
            Flight newFlight = this.flightDtoToFlightConverter.convert(flightDTO);
            newFlight.setDepartureAirport(departureAirport);
            newFlight.setArrivalAirport(arrivalAirport);
            Flight savedFlight = this.flightService.createFlight(newFlight);
            FlightDTO savedFlightDTO = this.flightToFlightDtoConverter.convert(savedFlight);
            return new Result(true, StatusCode.SUCCESS, "Add Success", savedFlightDTO);
        } catch (Exception e) {
            return null;
        }
    }

    @PutMapping("/{id}")
    public Result updateFlightDetails(@PathVariable long id, @RequestBody FlightDTO flightDTO) {
        try {
            Airport departureAirport = airportService.getAirportByCity(flightDTO.getDepartureAirport().getCity());
            Airport arrivalAirport = airportService.getAirportByCity(flightDTO.getArrivalAirport().getCity());
            Flight update = this.flightDtoToFlightConverter.convert(flightDTO);
            update.setDepartureAirport(departureAirport);
            update.setArrivalAirport(arrivalAirport);
            Flight updatedFlight = this.flightService.updateFlight(id, update);
            FlightDTO updatedFlightDTO = this.flightToFlightDtoConverter.convert(updatedFlight);
            return new Result(true, StatusCode.SUCCESS, "Update Success", updatedFlightDTO);
        } catch (Exception e) {
            return null;
        }
    }

    @DeleteMapping("/{id}")
    public Result deleteFlightDetails(@PathVariable("id") long id) {
        try {
            this.flightService.deleteFlight(id);
            return new Result(true, StatusCode.SUCCESS, "Delete Success");
        } catch (Exception e) {
            return null;
        }
    }

    @PostMapping("/search")
    public Result searchFlights(@RequestBody FlightDTO flightDTO) {
        Specification<Flight> specification = new Specification<Flight>() {
            @Override
            public Predicate toPredicate(Root<Flight> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Predicate departureAirport = criteriaBuilder.equal(root.get("departureAirport").get("city"), flightDTO.getDepartureAirport().getCity());
                Predicate arrivalAirport = criteriaBuilder.equal(root.get("arrivalAirport").get("city"), flightDTO.getArrivalAirport().getCity());
                Predicate departureDateTime = criteriaBuilder.equal(root.get("departureDateTime"), flightDTO.getDepartureDateTime());
                Predicate returnDateTime;
                if (flightDTO.getReturnDateTime() != null) {
                    returnDateTime = criteriaBuilder.equal(root.get("returnDateTime"), flightDTO.getReturnDateTime());
                } else {
                    returnDateTime = criteriaBuilder.isNull(root.get("returnDateTime"));
                }
                Predicate predicate = criteriaBuilder.and(departureAirport, arrivalAirport, departureDateTime, returnDateTime);
                return predicate;
            }
        };

        List<Flight> searchedFlights = flightService.searchFlights(specification);
        return new Result(true, StatusCode.SUCCESS, "Search Success", searchedFlights);
    }
}
