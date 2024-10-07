package com.backend.techstock.model;

import java.util.List;

import org.springframework.jdbc.core.simple.JdbcClient;

import com.backend.techstock.repository.salesToInsert;

public class SalesModel { 
    private final JdbcClient jdbcClient;

    public SalesModel(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<salesToInsert> findAll(){
        return jdbcClient.sql("SELECT * FROM sales").query(salesToInsert.class).list();
    }
    
    // Vai precisar de um join com a sales_product para criar a tela de editar venda
    //public List<sales> findSale(){
    //    return jdbcClient.sql("SELECT * FROM sales").query(sales.class).list();
    //}

    public Integer create(salesToInsert sale){
        return jdbcClient.sql("INSERT INTO sales(name, description,discount,date_time,id_users)" +
                              "VALUES (:name, :description, :discount, :date_time, :id_users)")
                         .param("name", sale.name())
                         .param("description", sale.description())
                         .param("discount", sale.discount())
                         .param("date_time", sale.date_time())
                         .param("id_users", sale.id_users())
                         .update();
    }

    public Integer updateSale(salesToInsert sale){
        return jdbcClient.sql(" UPDATE sales SET name = :name,"+
                              " description = :description," +
                              " discount = :discount,"+
                              " date_time = :date_time,"+
                              " id_users = :id_users"+
                              " WHERE id = :id")
                         .param("name", sale.name())
                         .param("description", sale.description())
                         .param("discount", sale.discount())
                         .param("date_time", sale.date_time())
                         .param("id_users", sale.id_users())
                         .param("id", sale.id())
                         .update();
    }

    public Integer deleteSale(int id){
        return jdbcClient.sql("DELETE FROM sales WHERE id = :id")
                         .param("id", id)
                         .update();
    }
}
