package com.backend.techstock.repository;

import java.time.LocalDateTime;

public record logsView(String description, LocalDateTime datetime, String user_name) {}
