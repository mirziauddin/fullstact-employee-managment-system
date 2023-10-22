package com.codewitharzoo.fullstackbackend.controller;

import com.codewitharzoo.fullstackbackend.exception.UserNotFoundException;
import com.codewitharzoo.fullstackbackend.model.User;
import com.codewitharzoo.fullstackbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin("http://localhost:3000")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    //add user
    @PostMapping("/user")
     User newUser(@RequestBody User newUser){
        return userRepository.save(newUser);
    }
    //find all users
    @GetMapping("/users")
    Iterable<User> getAllUser(){
        return userRepository.findAll();
    }

    //find by

    @GetMapping("/user/{id}")
    User getUserById(@PathVariable Long id){
        return userRepository.findById(id).orElseThrow(()->new UserNotFoundException(id));
    }

    //update user
    @PutMapping("/user/{id}")
    User updateUser(@RequestBody User newUser,@PathVariable Long id){
        return userRepository.findById(id).map(user -> {
                user.setName(newUser.getName());
                user.setUserName(newUser.getUserName());
                user.setEmail(newUser.getEmail());
                return userRepository.save(user);
        }).orElseThrow(() ->new UserNotFoundException(id));
    }

    @DeleteMapping("/user/{id}")
    String deleteUser(@PathVariable Long id){
        if (!userRepository.existsById(id)) {

            throw new UserNotFoundException(id);
        }
        userRepository.deleteById(id);
        return "User with id "+id+" has been deleted success.";
    }

}
