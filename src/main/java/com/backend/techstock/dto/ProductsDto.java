package com.backend.techstock.dto;

import com.backend.techstock.repository.brands;

public record ProductsDto(int id, 
                         String name, 
                         String description,
                         double price,
                         int quantity, 
                         String thumbnailPathname, 
                         brands idBrand) {} 
