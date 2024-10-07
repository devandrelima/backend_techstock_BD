package com.backend.techstock.model;

import java.util.List;

import org.springframework.jdbc.core.simple.JdbcClient;

import com.backend.techstock.repository.brands;
import com.backend.techstock.repository.users;

public class BrandsModel { 
    private final JdbcClient jdbcClient;

    public BrandsModel(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<brands> findAll(){
        return jdbcClient.sql("SELECT * FROM brands").query(brands.class).list();
    }

    public brands findBrand(String brandName){
        return jdbcClient.sql("SELECT * FROM brands WHERE name = :name")
                         .param("name", brandName)
                         .query(brands.class)
                         .single();
    }

}
