package com.backend.techstock.model;

import java.util.List;

import org.springframework.jdbc.core.simple.JdbcClient;

import com.backend.techstock.controller.UsuarioLogado;
import com.backend.techstock.repository.salesProducts;
import com.backend.techstock.repository.users;

import java.sql.*;

public class SalesProductModel {
    private final JdbcClient jdbcClient;

    public SalesProductModel(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<salesProducts> findAll(){
        return jdbcClient.sql("SELECT * FROM sales_product").query(salesProducts.class).list();
    }

    public Integer insert(salesProducts saleProduct){
        System.out.println(UsuarioLogado.globalVariable + "AAAAAAAAAAAAAAAAAAAAAAAAA");
        jdbcClient.sql("UPDATE products SET modified_by = :user_id WHERE id = :id")
            .param("user_id", UsuarioLogado.globalVariable)
            .param("id", saleProduct.idProduct())
            .update();

        int result = jdbcClient.sql("INSERT INTO sales_product(quantity, price, id_product, id_sales, modified_by)" +
                               " VALUES (:quantity, :price, :id_product, :id_sales, :modified_by)")
                         .param("quantity", saleProduct.quantity())
                         .param("price", saleProduct.price())
                         .param("id_product", saleProduct.idProduct())
                         .param("id_sales", saleProduct.id_sales())
                         .param("modified_by", UsuarioLogado.globalVariable)
                         .update();

        String sql = "SELECT sub_quantity_product(" + saleProduct.idProduct() + "," + saleProduct.quantity() + ");";

        try (
            Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/techstock", "postgres", "acesso");
            Statement stmt = conn.createStatement();
            CallableStatement callableStatement = conn.prepareCall(sql);
        ) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e);
        }

        return result;
    }

     public Integer update(salesProducts saleProduct){
        return jdbcClient.sql("UPDATE sales_product SET quantity = :quantity, price = :price, modified_by = :modified_by WHERE id = :id")                
                        .param("id", saleProduct.id())
                        .param("quantity", saleProduct.quantity())
                        .param("price", saleProduct.price())
                        .param("modified_by", UsuarioLogado.globalVariable)
                        .update();
    }

     public Integer delete(int id){
        return jdbcClient.sql("DELETE FROM sales_product WHERE id = :id")
                         .param("id", id)
                         .update();
    }
}
