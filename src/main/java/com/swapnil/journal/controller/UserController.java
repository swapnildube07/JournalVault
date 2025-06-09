package com.swapnil.journal.controller;

import com.swapnil.journal.Entity.JournalEntry;
import com.swapnil.journal.Entity.User;
import com.swapnil.journal.apiresponse.WeatherResponse;
import com.swapnil.journal.repositary.UserRepository;
import com.swapnil.journal.service.JournalEntryService;
import com.swapnil.journal.service.UserService;
import com.swapnil.journal.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WeatherService weatherService;

    @PutMapping
    public ResponseEntity<String> updateUser(@RequestBody User userRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User userInDb = userService.findByUserName(username);
        if (userInDb == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("User '" + username + "' not found");
        }

        userInDb.setPassword(userRequest.getPassword());
        userInDb.setUsername(userRequest.getUsername());

        try {
            userService.saveNewUser(userInDb);
            return ResponseEntity.ok("User updated successfully");
        } catch (org.springframework.dao.DuplicateKeyException ex) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("Update failed: that username is already in use");
        }
    }


    @DeleteMapping
    public  ResponseEntity<?> deleteUserByid(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        userRepository.deleteByUsername(authentication.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @GetMapping
    public ResponseEntity<?> greetings() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        WeatherResponse mumbaiWeather = weatherService.getWeather("Mumbai");
        String greeting = "";

        if (mumbaiWeather != null && mumbaiWeather.getCurrent() != null) {
            greeting = " Weather Feels Like " + mumbaiWeather.getCurrent().getTemperature() + "°C";
        }

        return new ResponseEntity<>("Hi " + username + greeting, HttpStatus.OK);
    }




}
