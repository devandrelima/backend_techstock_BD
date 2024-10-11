package com.backend.techstock.dto;

import java.time.LocalDateTime;

public record SalesDto( 
              String saleName, 
              String saleDescription,
              double saleDiscount,
              LocalDateTime saleDate) {} 
