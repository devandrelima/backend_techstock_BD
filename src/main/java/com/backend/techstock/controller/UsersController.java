package com.backend.techstock.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.techstock.model.UsersModel;
import com.backend.techstock.repository.changeName;
import com.backend.techstock.repository.messageResponse;
import com.backend.techstock.repository.users;

@RestController
@RequestMapping("/users")
public class UsersController {
    @Autowired
    private JdbcClient jdbcClient;

    @GetMapping("/all")
    public List<users> listAllUsers() {
        UsersModel usersModel = new UsersModel(jdbcClient);
        return usersModel.findAll();
    }

    @GetMapping("/{username}")
    public ResponseEntity listUser(@PathVariable String username) {
        UsersModel usersModel = new UsersModel(jdbcClient);
        messageResponse message;
        
        try{
            return new ResponseEntity<>(usersModel.findUser(username), HttpStatus.OK);
        } catch(EmptyResultDataAccessException e){
            message = new messageResponse("Usuário não encontrado");
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
    }
    
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody users user) {
        UsersModel usersModel = new UsersModel(jdbcClient);
        messageResponse message;
        users response = null;
        try {
            response = usersModel.findUser(user.name());
        } catch (EmptyResultDataAccessException e) {
            message = new messageResponse("Nome de usuário incorreto.");
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
        
        if(response.password().equals(user.password())){
            UsuarioLogado.globalVariable = response.id();
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        message = new messageResponse("Senha incorreta.");
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @PostMapping
    public ResponseEntity insertUser(@RequestBody users user) {
        UsersModel usersModel = new UsersModel(jdbcClient);
        messageResponse message;

        try {
            usersModel.create(user);   
        } catch (DuplicateKeyException e) {
            message = new messageResponse("Usuário já existe.");
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
        message = new messageResponse("Usuário criado com sucesso");
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PutMapping("/newpassword")
    @Transactional                                                                      
    public ResponseEntity updateUserPassword(@RequestBody users user) {
        UsersModel usersModel = new UsersModel(jdbcClient);
        users userBox;
        messageResponse message;

        try{
            userBox = usersModel.findUser(user.name());
        } catch(EmptyResultDataAccessException e){
            message = new messageResponse("Usuário não existe.");
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }

        usersModel.passwordUpdate(user);   
        message = new messageResponse("Senha atualizada");
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PutMapping("/newusername")
    @Transactional                                                                      
    public ResponseEntity updateUserName(@RequestBody changeName newUserName) {
        UsersModel usersModel = new UsersModel(jdbcClient);
        messageResponse message;
          
        try {
            usersModel.nameUpdate(newUserName);   
        } catch (DuplicateKeyException e) {
            message = new messageResponse("Nome de usuário já existe, escolha outro.");
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
        message = new messageResponse("Nome de usuário atualizado");
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity deleteUser(@RequestBody users user){
        UsersModel usersModel = new UsersModel(jdbcClient);
        users userBox;
        messageResponse message;

        try{
            userBox = usersModel.findUser(user.name());
        } catch(EmptyResultDataAccessException e){
            message = new messageResponse("Usuário não existe!");
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }

        if(userBox.password().equals(user.password())){
            usersModel.deleteUser(user); 
            message = new messageResponse("Usuário Deletado com sucesso.");
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else {
            message = new messageResponse("Senha incorreta, não podemos deletar esse usuário.");
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
    }
}
