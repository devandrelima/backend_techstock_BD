package com.backend.techstock.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    public List<users> listAllUsers() {
        UsersModel usersModel = new UsersModel(jdbcClient);
        return usersModel.findAll();
    }

    @GetMapping("/{nameUser}")
    public Optional<users> listUserByName(@PathVariable String nameUser) {
        UsersModel usersModel = new UsersModel(jdbcClient);
    
        return usersModel.findByName(nameUser);
        
    }

    @PostMapping
    public ResponseEntity insertUser2(@RequestBody users user) {
        UsersModel usersModel = new UsersModel(jdbcClient);
        try {
            boolean test = usersModel.findByName(user.name()).isEmpty(); 
            
            return ResponseEntity.ok("O usuário já existe");
        } catch (NullPointerException e) {
            usersModel.create(user);
            return ResponseEntity.ok("Usuário criado com sucesso");
        }
    }
}
