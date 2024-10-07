package com.backend.techstock.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.techstock.model.BrandsModel;
import com.backend.techstock.model.UsersModel;
import com.backend.techstock.repository.brands;
import com.backend.techstock.repository.messageResponse;
import com.backend.techstock.repository.users;

@RestController
@RequestMapping("/brands")
public class BrandsController {
    @Autowired
    private JdbcClient jdbcClient;

    @GetMapping
    public List<brands> listAllUsers() {
        BrandsModel brandsModel = new BrandsModel(jdbcClient);
        return brandsModel.findAll();
    }

    @GetMapping("/{brandName}")
    public ResponseEntity listUser(@PathVariable String brandName) {
        BrandsModel brandsModel = new BrandsModel(jdbcClient);
        messageResponse message;
        try{
            return new ResponseEntity<>(brandsModel.findBrand(brandName), HttpStatus.OK);
        } catch(EmptyResultDataAccessException e){
            message = new messageResponse("Empresa não encontrada");
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
    }
}
