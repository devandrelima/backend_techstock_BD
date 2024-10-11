package com.backend.techstock.model;

import java.util.List;

import org.springframework.jdbc.core.simple.JdbcClient;

import com.backend.techstock.repository.logs;
import com.backend.techstock.repository.logsView;

public class LogsModel {
 private final JdbcClient jdbcClient;

    public LogsModel(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }
    
    public List<logsView> findAll(){
        return jdbcClient.sql("SELECT * FROM log_user_view").query(logsView.class).list();
    }

    public Integer create(logs log){
        return jdbcClient.sql("INSERT INTO logs(description,datetime,id_user) VALUES (:description, :datetime, :id_user)")
                         .param("description", log.description())
                         .param("datetime", log.datetime())
                         .param("id_user", log.id_user())
                         .update();
    }
    
    public Integer logUpdate(logs log){
        return jdbcClient.sql("UPDATE logs SET description = :description, datetime = :datetime, id_user = :id_user WHERE id = :id")
                            .param("description", log.description())
                            .param("datetime", log.datetime())
                            .param("id_user", log.id_user())
                            .param("id", log.id())
                            .update();
    }

    public Integer deleteLog(int id){
        return jdbcClient.sql("DELETE FROM logs WHERE id = :id")
                         .param("id", id)
                         .update();
    }
}
