package com.backend.techstock.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.techstock.model.SalesProductModel;
import com.backend.techstock.repository.messageResponse;
import com.backend.techstock.repository.salesProducts;

@RestController
@RequestMapping("/saleproducts")
public class SalesProductsController {
    @Autowired
    private JdbcClient jdbcClient;

    @GetMapping()
    public List<salesProducts> listAllSaleProducts() {
        SalesProductModel salesProductModel = new SalesProductModel(jdbcClient);
        return salesProductModel.findAll();
    }

    @PostMapping
    public ResponseEntity insertOneSaleProduct(@RequestBody salesProducts product) {
        SalesProductModel salesProductModell = new SalesProductModel(jdbcClient);

        salesProductModell.insert(product);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // Um "post" que será chamado no sales
    public ResponseEntity insertSaleProducts(List<salesProducts> salesProducts) {
        SalesProductModel salesProductModell = new SalesProductModel(jdbcClient);

        for(int i = 0; i < salesProducts.size(); i++){
            salesProductModell.insert(salesProducts.get(i));   
        }

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    
}
