package com.backend.techstock.repository;

public record products(int id, 
                       String name,
                       String description, 
                       double price, 
                       int quantity, 
                       String thumbnailPathname, 
                       int idBrand, 
                       String nameBrand) {}
