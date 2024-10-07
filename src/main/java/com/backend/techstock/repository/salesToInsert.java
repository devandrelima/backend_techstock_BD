package com.backend.techstock.repository;

import java.time.LocalDateTime;

public record salesToInsert(int id, 
String name,
String description,
double discount,
LocalDateTime date_time,
int id_users) {}

