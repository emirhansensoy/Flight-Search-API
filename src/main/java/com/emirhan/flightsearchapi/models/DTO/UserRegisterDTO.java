package com.emirhan.flightsearchapi.models.DTO;

public class UserRegisterDTO {
    String username;
    String password;

    public UserRegisterDTO() {
    }

    public UserRegisterDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
