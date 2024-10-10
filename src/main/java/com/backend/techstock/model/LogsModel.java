package com.backend.techstock.model;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import com.backend.techstock.repository.logs;
import com.backend.techstock.repository.users;

import java.util.List;
@Repository
public class LogsModel {
 private final JdbcClient jdbcClient;

    public LogsModel(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<logs> findAll(){
        return jdbcClient.sql("SELECT * FROM logs").query(logs.class).list();
    }
    
}
