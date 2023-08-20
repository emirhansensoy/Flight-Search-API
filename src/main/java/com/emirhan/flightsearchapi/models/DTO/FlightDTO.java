package com.emirhan.flightsearchapi.models.DTO;

import com.emirhan.flightsearchapi.models.Airport;

import java.time.LocalDateTime;

public class FlightDTO {
    AirportDTO departureAirport;
    AirportDTO arrivalAirport;
    LocalDateTime departureDateTime;
    LocalDateTime returnDateTime;

    public FlightDTO() {
    }

    public FlightDTO(AirportDTO departureAirport, AirportDTO arrivalAirport, LocalDateTime departureDateTime, LocalDateTime returnDateTime) {
        this.departureAirport = departureAirport;
        this.arrivalAirport = arrivalAirport;
        this.departureDateTime = departureDateTime;
        this.returnDateTime = returnDateTime;
    }

    public AirportDTO getDepartureAirport() {
        return departureAirport;
    }

    public void setDepartureAirport(AirportDTO departureAirport) {
        this.departureAirport = departureAirport;
    }

    public AirportDTO getArrivalAirport() {
        return arrivalAirport;
    }

    public void setArrivalAirport(AirportDTO arrivalAirport) {
        this.arrivalAirport = arrivalAirport;
    }

    public LocalDateTime getDepartureDateTime() {
        return departureDateTime;
    }

    public void setDepartureDateTime(LocalDateTime departureDateTime) {
        this.departureDateTime = departureDateTime;
    }

    public LocalDateTime getReturnDateTime() {
        return returnDateTime;
    }

    public void setReturnDateTime(LocalDateTime returnDateTime) {
        this.returnDateTime = returnDateTime;
    }
}
