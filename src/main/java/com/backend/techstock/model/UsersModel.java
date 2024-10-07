package com.backend.techstock.model;

import java.util.List;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import com.backend.techstock.repository.changeName;
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

    public users findUser(String userName){
        return jdbcClient.sql("SELECT * FROM users WHERE name = :name")
                         .param("name", userName)
                         .query(users.class)
                         .single();
    }


    public Integer create(users user){
        return jdbcClient.sql("INSERT INTO users(name, password) VALUES (:name, :password)")
                         .param("name", user.name())
                         .param("password", user.password())
                         .update();
    }

    public Integer nameUpdate(changeName newUserName){
        return jdbcClient.sql("UPDATE users SET name = :newName WHERE name = :oldName")
                         .param("newName", newUserName.newName())
                         .param("oldName", newUserName.oldName())
                         .update();
    }

    public Integer passwordUpdate(users user){
        return jdbcClient.sql("UPDATE users SET password = :newpassword WHERE name = :name")
                         .param("newpassword", user.password())
                         .param("name", user.name())
                         .update();
    }

    public Integer deleteUser(users user){
        return jdbcClient.sql("DELETE FROM users WHERE name = :name")
                         .param("name", user.name())
                         .update();
    }
}
