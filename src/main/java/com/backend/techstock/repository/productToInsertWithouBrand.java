package com.backend.techstock.repository;

public record productToInsertWithouBrand(
int id, 
String name,
String description, 
double price, 
int quantity, 
String thumbnailPathname
) {

}
