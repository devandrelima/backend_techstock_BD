package com.backend.techstock.dto;

public record ProductSaleDto(String name,
double price,
String brand,
int quantity,
int id,
String thumbnail_pathname) {}
