package org.example.lab5.config;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.core.annotation.Order;

@Configuration
@Profile({"dev", "test"})
public class SchemaCompatibilityConfig {
    @Bean
    @Order(0)
    CommandLineRunner fixLegacyColumnNames(JdbcTemplate jdbcTemplate) {
        return args -> {
            dropColumnIfExists(jdbcTemplate, "book_copies", "copy_code");
            dropColumnIfExists(jdbcTemplate, "library_cards", "card_number");
            dropColumnIfExists(jdbcTemplate, "library_cards", "issue_date");
            dropColumnIfExists(jdbcTemplate, "library_cards", "expire_date");
            dropColumnIfExists(jdbcTemplate, "readers", "full_name");
            dropColumnIfExists(jdbcTemplate, "readers", "phone_number");
        };
    }

    private void dropColumnIfExists(JdbcTemplate jdbcTemplate, String tableName, String columnName) {
        Integer count = jdbcTemplate.queryForObject(
                """
                SELECT COUNT(*)
                FROM information_schema.columns
                WHERE table_schema = DATABASE()
                  AND table_name = ?
                  AND column_name = ?
                """,
                Integer.class,
                tableName,
                columnName
        );

        if (count != null && count > 0) {
            jdbcTemplate.execute("ALTER TABLE " + tableName + " DROP COLUMN " + columnName);
        }
    }
}
