package com.emirhan.flightsearchapi.services;

import com.emirhan.flightsearchapi.models.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface IUserService extends UserDetailsService {
    User createUser(User user);

    User updateUser(long id, User user);

    void deleteUser(long id);

    User getUser(long id);

    List<User> getAllUsers();
}
