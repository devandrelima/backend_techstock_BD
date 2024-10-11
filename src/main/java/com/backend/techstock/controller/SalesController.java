package com.backend.techstock.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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

import com.backend.techstock.dto.SaleWithProductsDto;
import com.backend.techstock.dto.SalesDto;
import com.backend.techstock.model.SalesModel;
import com.backend.techstock.repository.messageResponse;
import com.backend.techstock.repository.sales;
import com.backend.techstock.repository.salesToInsert;

@RestController
@RequestMapping("/sales")
public class SalesController {
    @Autowired
    private JdbcClient jdbcClient;

    @GetMapping
    public List<sales> listAllUsers() {
        SalesModel salesModel = new SalesModel(jdbcClient);
        List<salesToInsert> listSales = salesModel.findAll();
        List<sales> listSalesDateFormated = new ArrayList<>();
        String hourFormated;
        
        for(int i = 0; i < listSales.size(); i++){
            hourFormated = listSales.get(i).date_time().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));

            listSalesDateFormated.add(new sales(listSales.get(i).id(), 
                                                listSales.get(i).name(), 
                                                listSales.get(i).description(),
                                                listSales.get(i).discount(), 
                                                hourFormated, 
                                                listSales.get(i).id_users())); 
        }
        
        return listSalesDateFormated;
    }

    @GetMapping("/{saleId}")
    public SaleWithProductsDto getSaleById(@PathVariable int saleId) {
        SalesModel salesModel = new SalesModel(jdbcClient);

        return salesModel.findSaleById(saleId);
    }


    @PostMapping
    public ResponseEntity insertSale(@RequestBody sales sale) {
        SalesModel salesModel = new SalesModel(jdbcClient);
        messageResponse message;

        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"); // Deve estar de acordo com a string entregue do frontend
        LocalDateTime result = LocalDateTime.parse(sale.date_time(), format);

        salesToInsert salestoinsert = new salesToInsert(sale.id(), 
                                                        sale.name(),
                                                        sale.description(), 
                                                        sale.discount(), 
                                                        result, 
                                                        sale.id_users());
                                                        
        salesModel.create(salestoinsert);   
        
        message = new messageResponse("Venda registrada com sucesso");
        return new ResponseEntity<>(message, HttpStatus.OK);
    } 

    @PutMapping()
    @Transactional                                                                      
    public ResponseEntity updateSale(@RequestBody sales sale) {
        SalesModel salesModel = new SalesModel(jdbcClient);
        messageResponse message;
          
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"); // Deve estar de acordo com a string entregue do frontend
        LocalDateTime result = LocalDateTime.parse(sale.date_time(), format);

        salesToInsert salestoinsert = new salesToInsert(sale.id(), 
                                                        sale.name(),
                                                        sale.description(), 
                                                        sale.discount(), 
                                                        result, 
                                                        sale.id_users());
      
        salesModel.updateSale(salestoinsert);                                     
        message = new messageResponse("Alterações salvas.");
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deleteSale(@PathVariable int id){
        SalesModel salesModel = new SalesModel(jdbcClient);
        messageResponse message;

        salesModel.deleteSale(id);
        message = new messageResponse("Venda apagada.");
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
