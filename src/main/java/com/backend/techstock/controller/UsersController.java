package com.backend.techstock.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
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
import com.backend.techstock.repository.newusername;
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

    @PostMapping
    public ResponseEntity insertUser(@RequestBody users user) {
        UsersModel usersModel = new UsersModel(jdbcClient);
        
        try {
            usersModel.create(user);   
        } catch (DuplicateKeyException e) {
            return ResponseEntity.ok("Usuário já existe.");
        }

        return ResponseEntity.ok("Usuário criado com sucesso");
    }

    // Atualiza a senha, mas não verifica se o usuário existe ou não, ou seja, você pode atualizar a senha de um usuário
    // que nem existe kkkkk
    @PutMapping("/newpassword")
    @Transactional                                                                      
    public ResponseEntity updateUserPassword(@RequestBody users user) {
        UsersModel usersModel = new UsersModel(jdbcClient);

        usersModel.passwordUpdate(user);   
        
        return ResponseEntity.ok("Senha atualizada");
    }

    @PutMapping("/newusername")
    @Transactional                                                                      
    public ResponseEntity updateUserName(@RequestBody newusername newUserName) {
        UsersModel usersModel = new UsersModel(jdbcClient);
          
        try {
            usersModel.nameUpdate(newUserName);   
        } catch (DuplicateKeyException e) {
            return ResponseEntity.ok("Nome de usuário já existe, escolha outro.");
        }

        return ResponseEntity.ok("Nome de usuário atualizado");
    }
}
