package com.backend.techstock.dto;

import java.util.List;

public record SaleWithProductsDto( 
    SalesDto sale,
    List<ProductSaleDto> productSale) {} 