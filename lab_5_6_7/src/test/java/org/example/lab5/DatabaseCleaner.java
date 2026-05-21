package org.example.lab5;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DatabaseCleaner {
    private final JdbcTemplate jdbcTemplate;

    public DatabaseCleaner(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void clean() {
        jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS = 0");
        jdbcTemplate.execute("DELETE FROM reader_book_reservations");
        jdbcTemplate.execute("DELETE FROM book_copies");
        jdbcTemplate.execute("DELETE FROM books");
        jdbcTemplate.execute("DELETE FROM readers");
        jdbcTemplate.execute("DELETE FROM library_cards");
        jdbcTemplate.execute("DELETE FROM categories");
        jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS = 1");
    }
}
