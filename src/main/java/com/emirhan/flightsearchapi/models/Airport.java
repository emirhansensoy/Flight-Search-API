package com.emirhan.flightsearchapi.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "airports")
public class Airport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true, nullable = false)
    private String city;

    @JsonIgnore
    @OneToMany(mappedBy = "departureAirport", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Flight> departureFlights;
    @JsonIgnore
    @OneToMany(mappedBy = "arrivalAirport", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Flight> arrivalFlights;

    public Airport() {
    }

    public Airport(long id, String city) {
        this.id = id;
        this.city = city;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public List<Flight> getDepartureFlights() {
        return departureFlights;
    }

    public void setDepartureFlights(List<Flight> departureFlights) {
        this.departureFlights = departureFlights;
    }

    public List<Flight> getArrivalFlights() {
        return arrivalFlights;
    }

    public void setArrivalFlights(List<Flight> arrivalFlights) {
        this.arrivalFlights = arrivalFlights;
    }
}
