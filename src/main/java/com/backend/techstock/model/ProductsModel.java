package com.backend.techstock.model;

import java.sql.ResultSet;
import java.util.List;

import org.springframework.jdbc.core.simple.JdbcClient;

import com.backend.techstock.repository.brands;
import com.backend.techstock.repository.products;

public class ProductsModel {
    private final JdbcClient jdbcClient;

    public ProductsModel(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }
    
    public List<products> findAll(){
        return jdbcClient.sql("SELECT" +
                        "products.id," + 
                        "products.name," + 
                        "products.description," + 
                        "products.price," +
                        "products.quantity," + 
                        "products.thumbnail_pathname," + 
                        "brands.id AS idBrand," + 
                        "brands.name AS idBrand" + 
                        "FROM" + 
                        "products" + 
                        "JOIN" + 
                        "brands ON products.id_brand = brands.id;").query(products.class).list();
    }
}
