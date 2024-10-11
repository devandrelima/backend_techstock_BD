package com.backend.techstock.model;

import java.util.List;

import org.springframework.jdbc.core.simple.JdbcClient;

import com.backend.techstock.repository.logs;

public class LogsModel {
 private final JdbcClient jdbcClient;

    public LogsModel(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }
    
    public List<logs> findAll(){
        return jdbcClient.sql("SELECT * FROM log_user_view").query(logs.class).list();
    }
}
