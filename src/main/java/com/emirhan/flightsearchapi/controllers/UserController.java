package com.emirhan.flightsearchapi.controllers;

import com.emirhan.flightsearchapi.models.Airport;
import com.emirhan.flightsearchapi.models.DTO.AirportDTO;
import com.emirhan.flightsearchapi.models.DTO.UserRegisterDTO;
import com.emirhan.flightsearchapi.models.User;
import com.emirhan.flightsearchapi.services.IUserService;
import com.emirhan.flightsearchapi.system.Result;
import com.emirhan.flightsearchapi.system.StatusCode;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController {

    IUserService userService;

    public UserController(IUserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        //User admin = new User(0, "admin", "admin", "admin");
        //this.userService.createUser(admin);
    }

    @GetMapping("/{id}")
    @SecurityRequirement(name = "BasicAuth")
    public Result getUserDetails(@PathVariable("id") long id) {
        try {
            User user = this.userService.getUser(id);
            return new Result(true, StatusCode.SUCCESS, "Find One Success", user);
        } catch (Exception e) {
            return null;
        }
    }

    @GetMapping()
    @SecurityRequirement(name = "BasicAuth")
    public Result getAllUsersDetails() {
        try {
            List<User> foundUsers = this.userService.getAllUsers();
            return new Result(true, StatusCode.SUCCESS, "Find All Success", foundUsers);
        } catch (Exception e) {
            return null;
        }
    }

    @PostMapping()
    @SecurityRequirement(name = "BasicAuth")
    public Result createUserDetails(@RequestBody User user) {
        try {
            return new Result(true, StatusCode.SUCCESS, "Add Success", this.userService.createUser(user));
        } catch (Exception e) {
            return null;
        }
    }

    @PutMapping("/{id}")
    @SecurityRequirement(name = "BasicAuth")
    public Result updateUserDetails(@PathVariable long id, @RequestBody User user) {
        try {
            return new Result(true, StatusCode.SUCCESS, "Update Success", this.userService.updateUser(id, user));
        } catch (Exception e) {
            return null;
        }
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "BasicAuth")
    public Result deleteUserDetails(@PathVariable("id") long id) {
        try {
            this.userService.deleteUser(id);
            return new Result(true, StatusCode.SUCCESS, "Delete Success");
        } catch (Exception e) {
            return null;
        }
    }

    @PostMapping("/register")
    public Result register(@RequestBody UserRegisterDTO userRegisterDTO) {
        try {
            User newUser = new User();
            newUser.setUsername(userRegisterDTO.getUsername());
            newUser.setPassword(userRegisterDTO.getPassword());
            newUser.setId(0);
            newUser.setRole("user");
            this.userService.createUser(newUser);
            return new Result(true, StatusCode.SUCCESS, "Registration successful.");
        } catch (Exception e) {
            return null;
        }
    }
}
