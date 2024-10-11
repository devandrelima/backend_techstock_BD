package com.backend.techstock.dto;

import java.time.LocalDateTime;
import java.math.BigDecimal;

public record SalesDto( 
              String saleName, 
              String saleDescription,
              BigDecimal saleDiscount,
              LocalDateTime saleDate) {} 
