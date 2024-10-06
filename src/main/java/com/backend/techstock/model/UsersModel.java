package com.backend.techstock.model;

import java.util.List;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import com.backend.techstock.repository.users;

@Repository
public class UsersModel {
    private final JdbcClient jdbcClient;

    public UsersModel(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
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
}
