package com.backend.techstock.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.techstock.dto.ProductsDto;
import com.backend.techstock.model.ProductsModel;
import com.backend.techstock.repository.products;

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
}
