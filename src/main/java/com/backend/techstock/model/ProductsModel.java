package com.backend.techstock.model;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.simple.JdbcClient;

import com.backend.techstock.dto.ProductsDto;
import com.backend.techstock.repository.brands;
import com.backend.techstock.repository.products;

public class ProductsModel {
    private final JdbcClient jdbcClient;

    public ProductsModel(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<ProductsDto> findAll(){
        List<ProductsDto> productsDtoList = new ArrayList<>();
        List<products> productsList = jdbcClient.sql("SELECT" +
        " products.id," + 
        " products.name," + 
        " products.description," + 
        " products.price," +
        " products.quantity," + 
        " products.thumbnail_pathname," + 
        " brands.id AS idBrand," + 
        " brands.name AS nameBrand" + 
        " FROM" + 
        " products" + 
        " JOIN" + 
        " brands ON products.id_brand = brands.id;").query(products.class).list();


        for(int i = 0; i < productsList.size(); i++){
            productsDtoList.add(new ProductsDto(productsList.get(i).id(), 
            productsList.get(i).name(), 
            productsList.get(i).description(), 
            productsList.get(i).price(), 
            productsList.get(i).quantity(), 
            productsList.get(i).thumbnailPathname(), 
            new brands(productsList.get(i).idBrand(), productsList.get(i).nameBrand())));
        }

        return productsDtoList;
    }
}
