package com.backend.techstock.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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

import com.backend.techstock.dto.ProductsDto;
import com.backend.techstock.model.ProductsModel;
import com.backend.techstock.repository.messageResponse;
import com.backend.techstock.repository.productToInsert;

@CrossOrigin
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

    @GetMapping("/{id}")
    public ResponseEntity listProduct(@PathVariable int id) {
        ProductsModel productModel = new ProductsModel(jdbcClient);
        messageResponse message;
        
        try{
            return new ResponseEntity<>(productModel.findProduct(id), HttpStatus.OK);
        } catch(IndexOutOfBoundsException e){
            message = new messageResponse("Produto não encontrado");
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    public ResponseEntity insertProduct(@RequestBody productToInsert product) {
        ProductsModel productsModel = new ProductsModel(jdbcClient);
        messageResponse message;

        try {
            if (product.idBrand() == 0) {
                return new ResponseEntity<>(productsModel.createWithoutBrand(product), HttpStatus.OK);   
            } else {
                return new ResponseEntity<>(productsModel.create(product), HttpStatus.OK);   
            }
        } catch (DataIntegrityViolationException e) {
            message = new messageResponse("Produto já existe.");
            System.out.println(e);
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
    }
    
    @PutMapping()
    @Transactional                                                                      
    public ResponseEntity updateUserName(@RequestBody productToInsert product) {
        ProductsModel productsModel = new ProductsModel(jdbcClient);
        messageResponse message;
  
        if (product.idBrand() == 0) {
            productsModel.updateProductWithoutBrand(product);   
        } else {
            productsModel.updateProduct(product);   
        }
        
        message = new messageResponse("Produto atualizado com sucesso.");
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deleteProduct(@PathVariable int id){
        ProductsModel productsModel = new ProductsModel(jdbcClient);
        ProductsDto productBox;
        messageResponse message;

        try{
            productBox = productsModel.findProduct(id);
        } catch(IndexOutOfBoundsException e){
            message = new messageResponse("O produto não existe no banco de dados!");
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }

        productsModel.deleteProduct(id);
        message = new messageResponse("Produto deletado com sucesso.");
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
