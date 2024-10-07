package com.backend.techstock.model;

import java.util.List;

import org.springframework.jdbc.core.simple.JdbcClient;

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
                        " products.thumbnail_pathname," + 
                        "json_build_object(" + 
                        "brands.id AS brand," + 
                        "brands.name AS brand" + 
                        "FROM" + 
                        "products" + 
                        "JOIN" + 
                        "brands ON products.id_brand = brands.id;").query(products.class).list();
    }


}
