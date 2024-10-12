package com.backend.techstock.model;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.jdbc.core.simple.JdbcClient;

import com.backend.techstock.controller.UsuarioLogado;
import com.backend.techstock.dto.ProductSaleDto;
import com.backend.techstock.dto.SaleWithProductsDto;
import com.backend.techstock.dto.SalesDto;
import com.backend.techstock.repository.brands;
import com.backend.techstock.repository.sales;
import com.backend.techstock.repository.salesToInsert;

public class SalesModel { 
    private final JdbcClient jdbcClient;

    public SalesModel(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<salesToInsert> findAll(){
        return jdbcClient.sql("SELECT * FROM sales ORDER BY date_time DESC").query(salesToInsert.class).list();
    }
    
    // Vai precisar de um join com a sales_product para criar a tela de editar venda
    //public List<sales> findSale(){
    //    return jdbcClient.sql("SELECT * FROM sales").query(sales.class).list();
    //}









    public SaleWithProductsDto findSaleById(int saleId) {

        SalesDto salesDto = jdbcClient.sql("SELECT id, name, description, discount, date_time FROM sales WHERE id = :id")
                                    .param("id", saleId)                
                                    .query(SalesDto.class).single();

        List<ProductSaleDto> productSaleDto = jdbcClient.sql("SELECT product_name AS name , product_price AS price, brand_name AS brand,"+ 
        "quantity_sold AS quantity, product_photo AS thumbnail_pathname, id" +
        " FROM products_sale_datas WHERE id_sales = :id")
                                            .param("id", saleId)
                                            .query(ProductSaleDto.class).list();

        return new SaleWithProductsDto(salesDto, productSaleDto);
    }















    public salesToInsert create(salesToInsert sale){
        return jdbcClient.sql("INSERT INTO sales(name, description,discount,date_time,id_users,modified_by)" +
                              "VALUES (:name, :description, :discount, :date_time, :id_users, :modified_by) RETURNING *"
                            )
                         .param("name", sale.name())
                         .param("description", sale.description())
                         .param("discount", sale.discount())
                         .param("date_time", sale.date_time())
                         .param("id_users", UsuarioLogado.globalVariable) // O usuário que registra já é o que está logado
                         .param("modified_by", UsuarioLogado.globalVariable)
                         .query(salesToInsert.class).single();
    }

    public Integer updateSale(salesToInsert sale){
        return jdbcClient.sql(" UPDATE sales SET name = :name,"+
                              " description = :description," +
                              " discount = :discount,"+
                              " date_time = :date_time,"+
                              " id_users = :id_users,"+
                              " modified_by = :modified_by" +
                              " WHERE id = :id") 
                         .param("name", sale.name())
                         .param("description", sale.description())
                         .param("discount", sale.discount())
                         .param("date_time", sale.date_time())
                         .param("id_users", sale.id_users())
                         .param("id", sale.id())
                         .param("modified_by", UsuarioLogado.globalVariable)
                         .update();
    }

    public Integer deleteSale(int id){
        return jdbcClient.sql("DELETE FROM sales WHERE id = :id")
                         .param("id", id)
                         .update();
    }
}
