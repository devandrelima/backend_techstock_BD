package com.backend.techstock.dto;

import java.time.LocalDateTime;
import java.math.BigDecimal;

public record SalesDto( 
              Integer id,
              String name, 
              String description,
              BigDecimal discount,
              LocalDateTime date_time) {} 
