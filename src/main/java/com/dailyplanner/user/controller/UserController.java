package com.dailyplanner.user.controller;

import com.dailyplanner.user.dto.UserDTO;
import com.dailyplanner.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author petrov
 */
@RestController
@RequestMapping(path = "/api/user")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(path = "/add")
    public ResponseEntity addUser(@RequestBody UserDTO userDTO) {
        try {
            userService.addUser(userDTO);

            return new ResponseEntity(HttpStatus.CREATED);
        } catch (IllegalArgumentException exc) {
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
    }

    @GetMapping(path = "/{userId}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long userId) {
        try {
            UserDTO foundUser = userService.getUserById(userId);

            if (foundUser == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(foundUser, HttpStatus.OK);
            }
        } catch (Exception exc) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
