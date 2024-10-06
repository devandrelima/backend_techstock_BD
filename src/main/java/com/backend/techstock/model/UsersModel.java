package com.backend.techstock.model;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import com.backend.techstock.repository.users;

@Repository
public class UsersModel {
    private final JdbcClient jdbcClient;

    public UsersModel(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public Optional<users> findByName(String name) {
        Optional<users> user;
        
        try {
           user = Optional.ofNullable(jdbcClient.sql("SELECT * FROM users WHERE name = :name")
                          .param("name", name)
                          .query(users.class)
                          .single());
            
        } catch (DataAccessException ex) {
           user = null; // Se não encontrar no banco de dados, retorna null
        }

        return user;
    }


    public List<users> findAll(){
        return jdbcClient.sql("SELECT * FROM users").query(users.class).list();
    }

    public Integer create(users user){
        return jdbcClient.sql("INSERT INTO users(name, password) VALUES (:name, :password)")
                         .param("name", user.name())
                         .param("password", user.password())
                         .update();
    }

    public Integer nameUpdate(users user){
        return jdbcClient.sql("UPDATE users SET name = :name WHERE id = :id")
                         .param("name", user.name())
                         .param("id", user.id())
                         .update();
    }

    public Integer passwordUpdate(users user){
        return jdbcClient.sql("UPDATE users SET password = :password WHERE id = :id")
                         .param("name", user.password())
                         .param("id", user.id())
                         .update();
    }
}
