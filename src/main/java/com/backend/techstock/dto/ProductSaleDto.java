package com.backend.techstock.dto;

public record ProductSaleDto(String name,
double productPrice,
String brand,
int quantity,
double salePrice,
String productPhoto) {}
