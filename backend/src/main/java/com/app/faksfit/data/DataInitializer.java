package com.app.faksfit.data;

import jakarta.annotation.PostConstruct;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.charset.StandardCharsets;

@Component
public class DataInitializer {

    private final JdbcTemplate jdbcTemplate;

    public DataInitializer(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostConstruct
    public void initialize() {
        Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM SVEUCILISTE", Integer.class);

        if (count == null || count == 0) {
            try {
                ClassPathResource resource = new ClassPathResource("data-init.sql");
                try (InputStream inputStream = resource.getInputStream()) {
                    String sql = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
                    jdbcTemplate.execute(sql);
                }
                System.out.println("Database initialized with default data.");
            } catch (Exception e) {
                System.err.println("Failed to initialize database: " + e.getMessage());
            }
        } else {
            System.out.println("Database already initialized. Skipping data population.");
        }
    }
}
