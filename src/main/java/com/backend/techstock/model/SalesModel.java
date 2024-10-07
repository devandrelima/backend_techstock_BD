package com.backend.techstock.model;

import java.util.List;

import org.springframework.jdbc.core.simple.JdbcClient;

import com.backend.techstock.repository.sales;
import com.backend.techstock.repository.salesToInsert;
import com.backend.techstock.repository.users;

public class SalesModel { 
    private final JdbcClient jdbcClient;

    public SalesModel(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<sales> findAll(){
        return jdbcClient.sql("SELECT * FROM sales").query(sales.class).list();
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
}
