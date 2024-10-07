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

import com.backend.techstock.model.BrandsModel;
import com.backend.techstock.repository.brands;
import com.backend.techstock.repository.changeName;
import com.backend.techstock.repository.messageResponse;

@RestController
@RequestMapping("/brands")
public class BrandsController {
    @Autowired
    private JdbcClient jdbcClient;

    @GetMapping
    public List<brands> listAllBrands() {
        BrandsModel brandsModel = new BrandsModel(jdbcClient);
        return brandsModel.findAll();
    }

    @GetMapping("/{brandName}")
    public ResponseEntity listBrand(@PathVariable String brandName) {
        BrandsModel brandsModel = new BrandsModel(jdbcClient);
        messageResponse message;
        try{
            return new ResponseEntity<>(brandsModel.findBrand(brandName), HttpStatus.OK);
        } catch(EmptyResultDataAccessException e){
            message = new messageResponse("Empresa não encontrada");
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    public ResponseEntity insertBrand(@RequestBody brands brand) {
        BrandsModel brandsModel = new BrandsModel(jdbcClient);
        messageResponse message;

        try {
            brandsModel.create(brand);   
        } catch (DuplicateKeyException e) {
            message = new messageResponse("A empresa já está cadastrada.");
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
        message = new messageResponse("Empresa cadastrada com sucesso.");
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PutMapping
    @Transactional                                                                      
    public ResponseEntity updateUserName(@RequestBody changeName newUserName) {
        BrandsModel brandsModel = new BrandsModel(jdbcClient);
        messageResponse message;
          
        try {
            brandsModel.brandNameUpdate(newUserName);   
        } catch (DuplicateKeyException e) {
            message = new messageResponse("Não é possível alterar o nome, escolha outro.");
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
        message = new messageResponse("Nome da empresa atualizado");
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity deleteBrand(@RequestBody brands brand){
        BrandsModel brandsModel = new BrandsModel(jdbcClient);
        brands brandBox;
        messageResponse message;

        try{
            brandBox = brandsModel.findBrand(brand.name());
        } catch(EmptyResultDataAccessException e){
            message = new messageResponse("A empresa não existe no banco de dados!");
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }

        brandsModel.deleteBrand(brand);
        message = new messageResponse("Empresa deletada com sucesso.");
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
