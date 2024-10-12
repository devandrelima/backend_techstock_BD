package com.backend.techstock.model;

import java.util.List;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.core.simple.JdbcClient.MappedQuerySpec;

import com.backend.techstock.controller.UsuarioLogado;
import com.backend.techstock.repository.brands;
import com.backend.techstock.repository.changeName;

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

    public brands create(brands brand){
        return jdbcClient.sql("INSERT INTO brands(name,modified_by) VALUES (:name,:modified_by) RETURNING *")
                         .param("name", brand.name())
                         .param("modified_by", UsuarioLogado.globalVariable)
                         .query(brands.class).single();
    }

    
    public Integer brandNameUpdate(changeName newUserName){
        return jdbcClient.sql("UPDATE brands SET name = :newName, modified_by = :modified_by WHERE name = :oldName")
                         .param("newName", newUserName.newName())
                         .param("oldName", newUserName.oldName())
                         .param("modified_by", UsuarioLogado.globalVariable)
                         .update();
    }

    public Integer deleteBrand(brands brand){
        return jdbcClient.sql("DELETE FROM brands WHERE name = :name")
                         .param("name", brand.name())
                         .update();
    }
}
