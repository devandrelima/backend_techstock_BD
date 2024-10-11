package com.backend.techstock.model;

import java.util.List;

import org.springframework.jdbc.core.simple.JdbcClient;

import com.backend.techstock.controller.UsuarioLogado;
import com.backend.techstock.repository.salesProducts;
import com.backend.techstock.repository.users;

public class SalesProductModel {
    private final JdbcClient jdbcClient;

    public SalesProductModel(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<salesProducts> findAll(){
        return jdbcClient.sql("SELECT * FROM sales_product").query(salesProducts.class).list();
    }

    public Integer insert(salesProducts saleProduct){
        return jdbcClient.sql("INSERT INTO sales_product(quantity, price, id_product, id_sales, modified_by)" +
                               " VALUES (:quantity, :price, :id_product, :id_sales, :modified_by)")
                         .param("quantity", saleProduct.quantity())
                         .param("price", saleProduct.price())
                         .param("id_product", saleProduct.idProduct())
                         .param("id_sales", saleProduct.id_sales())
                         .param("modified_by", UsuarioLogado.globalVariable)
                         .update();
    }
}
