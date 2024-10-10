package com.backend.techstock.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.techstock.model.LogsModel;
import com.backend.techstock.repository.logs;

@RestController
@RequestMapping("/logs")
public class LogsController {
    @Autowired
    private JdbcClient jdbcClient;

    @GetMapping
    public List<logs> listAllLogs() {
        LogsModel logsModel = new LogsModel(jdbcClient);
        return logsModel.findAll();
    }
}
