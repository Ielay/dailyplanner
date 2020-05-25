package com.dailyplanner.user.controller;

import com.dailyplanner.user.dto.LoginDTO;
import com.dailyplanner.user.dto.UserDTO;
import com.dailyplanner.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping(path = "/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO) {
        try {
            String token = userService.login(loginDTO.username, loginDTO.password);

            return new ResponseEntity<>(token, HttpStatus.OK);
        } catch (Exception exc) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(path = "/add")
    public ResponseEntity<String> addUser(@RequestBody UserDTO userDTO) {
        try {
            String token = userService.addUser(userDTO);

            return new ResponseEntity<>(token, HttpStatus.CREATED);
        } catch (Exception exc) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }
}
