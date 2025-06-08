package com.swapnil.journal.controller;

import com.swapnil.journal.Entity.JournalEntry;
import com.swapnil.journal.Entity.User;
import com.swapnil.journal.repositary.UserRepository;
import com.swapnil.journal.service.JournalEntryService;
import com.swapnil.journal.service.UserService;
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


}
