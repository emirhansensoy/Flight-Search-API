package com.emirhan.flightsearchapi.system;

import com.emirhan.flightsearchapi.models.Airport;
import com.emirhan.flightsearchapi.models.DTO.AirportDTO;
import com.emirhan.flightsearchapi.models.DTO.FlightDTO;
import com.emirhan.flightsearchapi.models.DTO.conversions.FlightDtoToFlightConverter;
import com.emirhan.flightsearchapi.models.DTO.conversions.FlightToFlightDtoConverter;
import com.emirhan.flightsearchapi.models.Flight;
import com.emirhan.flightsearchapi.services.IAirportService;
import com.emirhan.flightsearchapi.services.IFlightService;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Random;

@Component
@EnableScheduling
public class Scheduler {

    IFlightService flightService;
    IAirportService airportService;
    FlightDtoToFlightConverter flightDtoToFlightConverter;
    final String uri = "http://localhost:8080/mockapi";
    RestTemplate restTemplate;
    FlightDTO[] flights;

    Scheduler(IFlightService flightService,
              FlightDtoToFlightConverter flightDtoToFlightConverter,
              IAirportService airportService) {
        this.flightService = flightService;
        this.airportService = airportService;
        this.flightDtoToFlightConverter = flightDtoToFlightConverter;
        restTemplate = new RestTemplate();
    }

    @RestController
    @RequestMapping("/mockapi")
    public class MockAPI {

        IAirportService airportService;

        MockAPI(IAirportService airportService) {
            this.airportService = airportService;
        }

        private FlightDTO createRandomFlightDTO() {
            AirportDTO departureAirport = new AirportDTO("City" + new Random().nextInt(30));

            AirportDTO arrivalAirport = new AirportDTO("City" + new Random().nextInt(30));

            LocalDateTime departureDateTime = LocalDateTime.now()
                    .plusMonths(new Random().nextInt(30))
                    .plusDays(new Random().nextInt(30))
                    .plusHours(new Random().nextInt(30));

            LocalDateTime returnDateTime = departureDateTime
                    .plusMonths(new Random().nextInt(30))
                    .plusDays(new Random().nextInt(30))
                    .plusHours(new Random().nextInt(30));

            boolean returnFlight = true;

            if (new Random().nextInt(2) == 1) returnFlight = false;

            FlightDTO flight = new FlightDTO(
                    departureAirport,
                    arrivalAirport,
                    departureDateTime,
                    returnFlight ? returnDateTime : null
            );

            return flight;
        }

        @GetMapping()
        public FlightDTO[] getFlights() {
            FlightDTO flights[] = new FlightDTO[5];
            for (int i = 0; i < 5; i++) {
                flights[i] = createRandomFlightDTO();
            }
            return flights;
        }
    }

    @Scheduled(cron = "@daily")
    public void scheduler() throws InterruptedException {
        flights = restTemplate.getForObject(uri, FlightDTO[].class);
        for (int i = 0; i < flights.length; i++) {

            //Check for departure airport with the given city name. If not exists create it.
            String departureCity = flights[i].getDepartureAirport().getCity();
            Airport departureAirport;
            try {
                departureAirport = airportService.getAirportByCity(departureCity);
            } catch (Exception e) {
                departureAirport = airportService.createAirport(new Airport(0, departureCity));
            }

            //Check for arrival airport with the given city name. If not exists create it.
            String arrivalCity = flights[i].getArrivalAirport().getCity();
            Airport arrivalAirport;
            try {
                arrivalAirport = airportService.getAirportByCity(arrivalCity);
            } catch (Exception e) {
                arrivalAirport = airportService.createAirport(new Airport(0, arrivalCity));
            }

            //Convert FlightDTO to Flight entity.
            Flight newFlight = this.flightDtoToFlightConverter.convert(flights[i]);

            //Set airport values.
            newFlight.setDepartureAirport(departureAirport);
            newFlight.setArrivalAirport(arrivalAirport);

            //Create flight
            this.flightService.createFlight(newFlight);
        }
    }
}
