package com.backend.techstock.repository;

import java.time.LocalDateTime;

public record logs(int id, String description, LocalDateTime datetime, int id_user) {}
