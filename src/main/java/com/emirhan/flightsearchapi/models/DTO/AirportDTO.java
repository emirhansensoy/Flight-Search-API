package com.emirhan.flightsearchapi.models.DTO;

public class AirportDTO {
    String city;

    public AirportDTO() {
    }

    public AirportDTO(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
