package com.libmanager.lib.controllers;

import com.libmanager.lib.domain.dto.UserDto;
import com.libmanager.lib.services.UserService;
import org.apache.catalina.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class UserController {

    UserService userService;

    UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        UserDto savedUser = userService.save(userDto);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<UserDto> fullUpdateUser(@PathVariable("id") Long id, @RequestBody UserDto userDto) {
        if(!userService.exists(id)) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        userDto.setId(id);
        UserDto savedUser = userService.save(userDto);
        return new ResponseEntity<>(savedUser, HttpStatus.OK);
    }

    @PatchMapping("/users/{id}")
    public ResponseEntity<UserDto> partialUpdateUser(@PathVariable("id") Long id, @RequestBody UserDto userDto) {
        if(!userService.exists(id)) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        UserDto savedUser = userService.partialUpdate(id, userDto);
        return new ResponseEntity<>(savedUser, HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity deleteUser(@PathVariable("id") Long id) {
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/users")
    public ResponseEntity<Page<UserDto>> getAllUsers(Pageable pageable) {
        Page<UserDto> foundUsers = userService.findAll(pageable);
        return new ResponseEntity<>(foundUsers, HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public  ResponseEntity<UserDto> getOneUser(@PathVariable("id") Long id) {
        Optional<UserDto> foundUser = userService.findOne(id);
        return foundUser.map(userDto -> new ResponseEntity<>(userDto, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}
