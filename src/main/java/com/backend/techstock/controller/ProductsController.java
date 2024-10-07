package com.backend.techstock.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.techstock.dto.ProductsDto;
import com.backend.techstock.model.ProductsModel;
import com.backend.techstock.model.UsersModel;
import com.backend.techstock.repository.messageResponse;
import com.backend.techstock.repository.products;
import com.backend.techstock.repository.users;

@RestController
@RequestMapping("/products")
public class ProductsController {
    @Autowired
    private JdbcClient jdbcClient;

    @GetMapping
    public List<ProductsDto> listAllProducts() {
        ProductsModel productsModel = new ProductsModel(jdbcClient);
        return productsModel.findAll();
    }

    @GetMapping("/{name}")
    public ResponseEntity listProduct(@PathVariable String name) {
        ProductsModel productModel = new ProductsModel(jdbcClient);
        messageResponse message;
        
        try{
            return new ResponseEntity<>(productModel.findProduct(name), HttpStatus.OK);
        } catch(IndexOutOfBoundsException e){
            message = new messageResponse("Produto não encontrado");
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    public ResponseEntity insertProduct(@RequestBody products product) {
        ProductsModel productsModel = new ProductsModel(jdbcClient);
        messageResponse message;

        try {
            productsModel.create(product);   
        } catch (DuplicateKeyException e) {
            message = new messageResponse("Produto já existe.");
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
        message = new messageResponse("Produto cadastrado com sucesso");
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
