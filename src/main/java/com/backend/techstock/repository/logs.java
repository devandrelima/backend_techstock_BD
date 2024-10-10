package com.backend.techstock.repository;

import java.time.LocalDateTime;

public record logs(int id, String description, String localDateTime, int user_id) {}
