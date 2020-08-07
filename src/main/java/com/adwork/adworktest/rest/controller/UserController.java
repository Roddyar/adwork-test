package com.adwork.adworktest.rest.controller;

import com.adwork.adworktest.db.entities.Response;
import com.adwork.adworktest.db.entities.User;
import com.adwork.adworktest.db.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService userService;

    @PostMapping(value = "/save")
    public Response saveUser(@RequestBody User user) {
        Response response = userService.saveOrUpdateUser(user);
        return response;
    }

}
