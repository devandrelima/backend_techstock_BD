package com.backend.techstock.repository;

import java.time.LocalDateTime;

public record logs(String description, LocalDateTime datetime, String user_name) {}
