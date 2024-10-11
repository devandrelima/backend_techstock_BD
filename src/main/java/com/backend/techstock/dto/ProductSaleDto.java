package com.backend.techstock.dto;

public record ProductSaleDto(String name,
double price,
String brand,
int quantity,
String thumbnail_pathname) {}
