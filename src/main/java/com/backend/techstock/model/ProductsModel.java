package com.backend.techstock.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.simple.JdbcClient;

import com.backend.techstock.dto.ProductsDto;
import com.backend.techstock.repository.brands;
import com.backend.techstock.repository.productToInsert;
import com.backend.techstock.repository.products;

public class ProductsModel {
    private final JdbcClient jdbcClient;

    public ProductsModel(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    private List<products> returnListProductsBD(int all, int id){

        String sql = "SELECT" +
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
        " brands ON products.id_brand = brands.id";

        if(all == 0){
            sql+=" WHERE products.id = :id";
            return jdbcClient.sql(sql)
                             .param("id", id)
                             .query(products.class).list();
        }

        return jdbcClient.sql(sql).query(products.class).list();
    }

    private ProductsDto transformProductsDto(List<products> productsList, int i){
        return new ProductsDto(productsList.get(i).id(), 
        productsList.get(i).name(), 
        productsList.get(i).description(), 
        productsList.get(i).price(), 
        productsList.get(i).quantity(), 
        productsList.get(i).thumbnailPathname(), 
        new brands(productsList.get(i).idBrand(), productsList.get(i).nameBrand()));

    }

    public List<ProductsDto> findAll(){
        List<ProductsDto> productsDtoList = new ArrayList<>(); 
        List<products> productsList = returnListProductsBD(1, 0);

        for(int i = 0; i < productsList.size(); i++){
            productsDtoList.add(transformProductsDto(productsList,i));
        }

        return productsDtoList;
    }
    
    public List<ProductsDto> findProduct(int id){
        List<ProductsDto> productsDtoList = new ArrayList<>(); 
        List<products> productsList = returnListProductsBD(0, id);

        productsDtoList.add(transformProductsDto(productsList,0));
        
        return productsDtoList;
    }

     public Integer create(productToInsert product){
        return jdbcClient.sql("INSERT INTO products(name,description,price,quantity,thumbnail_pathname,id_brand)" + //
                        " VALUES (:name, :description, :price, :quantity, :thumbnail_pathname, :id_brand)")
                         .param("name", product.name())
                         .param("description", product.description())
                         .param("price", product.price())
                         .param("quantity", product.quantity())
                         .param("thumbnail_pathname", product.thumbnailPathname())
                         .param("id_brand", product.idBrand())
                         .update();
    }

    public Integer deleteProduct(int id){
        return jdbcClient.sql("DELETE FROM products WHERE id = :id")
                         .param("id", id)
                         .update();
    }
}
