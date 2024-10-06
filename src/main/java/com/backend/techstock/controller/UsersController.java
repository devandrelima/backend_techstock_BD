package com.backend.techstock.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.techstock.model.UsersModel;
import com.backend.techstock.repository.users;

@RestController
@RequestMapping("/users")
public class UsersController {
    @Autowired
    private JdbcClient jdbcClient;

    @GetMapping
    public List<users> listUsers() {
        UsersModel usersModel = new UsersModel(jdbcClient);
        return usersModel.findAll();
    }

    @PostMapping
    public ResponseEntity insertUser(@RequestBody users user){
        UsersModel usersModel = new UsersModel(jdbcClient);
        usersModel.create(user);
        return ResponseEntity.ok().build();
    }
}
