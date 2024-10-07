package com.backend.techstock.repository;

public record sales(int id, 
                    String name,
                    String description,
                    double discount,
                    String date_time,
                    int id_users) {}
