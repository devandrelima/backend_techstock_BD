package com.backend.techstock.controller;

import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.techstock.model.SalesModel;
import com.backend.techstock.model.SalesProductModel;
import com.backend.techstock.model.UsersModel;
import com.backend.techstock.repository.messageResponse;
import com.backend.techstock.repository.salesProducts;
import com.backend.techstock.repository.salesProductsJson;
import com.backend.techstock.repository.users;

@CrossOrigin
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

   @PostMapping()
    public ResponseEntity insertSaleProducts(@RequestBody salesProductsJson salesProducts) {
        SalesProductModel salesProductModel = new SalesProductModel(jdbcClient);

        List<salesProducts> sales = new ArrayList<salesProducts>();
        for(int i = 0; i < salesProducts.products().size(); i++){
            sales.add(salesProductModel.insert(salesProducts.products().get(i)));
        }

        return new ResponseEntity<>(sales, HttpStatus.OK);
    }

    @PutMapping()
    @Transactional                                                                      
    public ResponseEntity updateSaleProduct(@RequestBody salesProducts saleProduct) {
        SalesProductModel salesProductModel = new SalesProductModel(jdbcClient);
        
        salesProductModel.update(saleProduct);
       
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deleteSaleProduct(@PathVariable int id){
        SalesProductModel salesProductModel = new SalesProductModel(jdbcClient);
        
        salesProductModel.delete(id);
       
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
