package com.backend.techstock.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.techstock.model.SalesModel;
import com.backend.techstock.model.UsersModel;
import com.backend.techstock.repository.messageResponse;
import com.backend.techstock.repository.sales;
import com.backend.techstock.repository.salesToInsert;
import com.backend.techstock.repository.users;

@RestController
@RequestMapping("/sales")
public class SalesController {
    @Autowired
    private JdbcClient jdbcClient;

    @GetMapping
    public List<sales> listAllUsers() {
        SalesModel salesModel = new SalesModel(jdbcClient);
        return salesModel.findAll();
    }

    @PostMapping
    public ResponseEntity insertSale(@RequestBody sales sale) {
        SalesModel salesModel = new SalesModel(jdbcClient);
        messageResponse message;

        
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime result = LocalDateTime.parse(sale.date_time(), format);

        salesToInsert salestoinsert = new salesToInsert(sale.id(), sale.name(), sale.description(), sale.discount(), result, sale.id_users());
        
        salesModel.create(salestoinsert);   
        
        message = new messageResponse("Venda registrada com sucesso");
        return new ResponseEntity<>(message, HttpStatus.OK);
    } 
}
