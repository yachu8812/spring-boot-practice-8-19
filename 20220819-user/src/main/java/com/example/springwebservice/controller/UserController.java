package com.example.springwebservice.controller;

import com.example.springwebservice.controller.dto.request.CreateUserRequest;
import com.example.springwebservice.controller.dto.request.UpdateUserRequest;
import com.example.springwebservice.controller.dto.response.StatusResponse;
import com.example.springwebservice.model.Entity.User;
import com.example.springwebservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping
    public List<User> getUserLists(@RequestParam(required = false)Integer age, @RequestParam int ageFilter) {

        List<User> response = userService.getUserList(age, ageFilter);

        return response;
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable int id) {

        User response = userService.getUserById(id);

        return response;
    }

    @PostMapping()
    public StatusResponse createUser(@RequestBody CreateUserRequest user) {

        String response = userService.createUser(user);

        return new StatusResponse(response);
    }


    @PutMapping("/{id}")
    public StatusResponse updateUser(@PathVariable int id, @RequestBody UpdateUserRequest user) {

        String response = userService.updateUser(id,user);

        return new StatusResponse(response);
    }

    @DeleteMapping("/{id}")
    public StatusResponse deleteUserById(@PathVariable int id) {
        String response = userService.deleteUserById(id);

        return new StatusResponse(response);
    }



} // Class end
