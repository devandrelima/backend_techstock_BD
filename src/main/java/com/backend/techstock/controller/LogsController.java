package com.backend.techstock.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.techstock.model.LogsModel;
import com.backend.techstock.repository.logs;
import com.backend.techstock.repository.logsView;
import com.backend.techstock.repository.messageResponse;

@RestController
@RequestMapping("/logs")
public class LogsController {
    @Autowired
    private JdbcClient jdbcClient;

    @GetMapping
    public List<logsView> listAllLogs() {
        LogsModel logsModel = new LogsModel(jdbcClient);
        return logsModel.findAll();
    }

    @PostMapping
    public ResponseEntity insertLog(@RequestBody logs log) {
        LogsModel logsModel = new LogsModel(jdbcClient);
        messageResponse message;

        logsModel.create(log);
        
        message = new messageResponse("Log cadastrado.");
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PutMapping
    @Transactional                                                                      
    public ResponseEntity updateLog(@RequestBody logs log) {
        LogsModel logsModel = new LogsModel(jdbcClient);
        messageResponse message;
   
       logsModel.logUpdate(log);

        message = new messageResponse("Log atualizado.");
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deleteBrand(@PathVariable int id){
        LogsModel logsModel = new LogsModel(jdbcClient);
        messageResponse message;
        
        logsModel.deleteLog(id);

        message = new messageResponse("Log deletado.");
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
