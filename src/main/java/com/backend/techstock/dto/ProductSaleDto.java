package com.backend.techstock.dto;

import java.util.Optional;

public record ProductSaleDto(Optional<String> name,
double price,
Optional<String> brand,
int quantity,
Optional<Integer> id,
String thumbnail_pathname,
int saleproduct_id) {}
