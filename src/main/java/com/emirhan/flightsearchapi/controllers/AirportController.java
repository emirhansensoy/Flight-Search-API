package com.emirhan.flightsearchapi.controllers;

import com.emirhan.flightsearchapi.models.Airport;
import com.emirhan.flightsearchapi.models.DTO.AirportDTO;
import com.emirhan.flightsearchapi.models.DTO.conversions.AirportDtoToAirportConverter;
import com.emirhan.flightsearchapi.models.DTO.conversions.AirportToAirportDtoConverter;
import com.emirhan.flightsearchapi.services.IAirportService;
import com.emirhan.flightsearchapi.system.Result;
import com.emirhan.flightsearchapi.system.StatusCode;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/airport")
@SecurityRequirement(name = "BasicAuth")
public class AirportController {

    IAirportService airportService;

    private final AirportToAirportDtoConverter airportToAirportDtoConverter;

    private final AirportDtoToAirportConverter airportDtoToAirportConverter;

    public AirportController(IAirportService airportService,
                             AirportToAirportDtoConverter airportToAirportDtoConverter,
                             AirportDtoToAirportConverter airportDtoToAirportConverter) {
        this.airportService = airportService;
        this.airportToAirportDtoConverter = airportToAirportDtoConverter;
        this.airportDtoToAirportConverter = airportDtoToAirportConverter;
    }

    @GetMapping("/{id}")
    @SecurityRequirement(name = "")
    public Result getAirportDetails(@PathVariable("id") long id) {
        try {
            Airport airport = this.airportService.getAirport(id);
            AirportDTO airportDTO = this.airportToAirportDtoConverter.convert(airport);
            return new Result(true, StatusCode.SUCCESS, "Find One Success", airportDTO);
        } catch (Exception e) {
            return null;
        }
    }

    @GetMapping()
    public Result getAllAirportsDetails() {
        try {
            List<Airport> foundAirports = this.airportService.getAllAirports();
            List<AirportDTO> airportDTOs = foundAirports.stream()
                    .map(this.airportToAirportDtoConverter::convert)
                    .collect(Collectors.toList());
            return new Result(true, StatusCode.SUCCESS, "Find All Success", airportDTOs);
        } catch (Exception e) {
            return null;
        }
    }

    @PostMapping()
    public Result createAirportDetails(@RequestBody AirportDTO airportDTO) {
        try {
            Airport newAirport = this.airportDtoToAirportConverter.convert(airportDTO);
            Airport savedAirport = this.airportService.createAirport(newAirport);
            AirportDTO savedAirportDTO = this.airportToAirportDtoConverter.convert(savedAirport);
            return new Result(true, StatusCode.SUCCESS, "Add Success", savedAirportDTO);
        } catch (Exception e) {
            return null;
        }
    }

    @PutMapping("/{id}")
    public Result updateAirportDetails(@PathVariable long id, @RequestBody AirportDTO airportDTO) {
        try {
            Airport update = this.airportDtoToAirportConverter.convert(airportDTO);
            Airport updatedAirport = this.airportService.updateAirport(id, update);
            AirportDTO updatedAirportDTO = this.airportToAirportDtoConverter.convert(updatedAirport);
            return new Result(true, StatusCode.SUCCESS, "Update Success", updatedAirportDTO);
        } catch (Exception e) {
            return null;
        }
    }

    @DeleteMapping("/{id}")
    public Result deleteAirportDetails(@PathVariable("id") long id) {
        try {
            this.airportService.deleteAirport(id);
            return new Result(true, StatusCode.SUCCESS, "Delete Success");
        } catch (Exception e) {
            return null;
        }
    }
}
