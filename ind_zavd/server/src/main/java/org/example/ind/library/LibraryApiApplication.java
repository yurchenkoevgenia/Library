package org.example.ind.library;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class LibraryApiApplication {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(LibraryApiApplication.class);

        // Fallback defaults keep the app on the expected port even if IDE output is stale.
        Map<String, Object> defaults = new HashMap<>();
        defaults.put("server.port", "8082");
        defaults.put("spring.application.name", "individual-library-server");
        defaults.put("spring.datasource.url", "jdbc:h2:mem:library_ind;MODE=MYSQL;DB_CLOSE_DELAY=-1;DATABASE_TO_LOWER=TRUE");
        defaults.put("spring.datasource.driver-class-name", "org.h2.Driver");
        defaults.put("spring.datasource.username", "sa");
        defaults.put("spring.datasource.password", "");
        defaults.put("spring.jpa.hibernate.ddl-auto", "update");
        defaults.put("spring.jpa.show-sql", "true");
        defaults.put("spring.jpa.properties.hibernate.format_sql", "true");
        defaults.put("spring.jpa.open-in-view", "false");
        defaults.put("spring.h2.console.enabled", "true");
        defaults.put("spring.profiles.active", "dev");

        application.setDefaultProperties(defaults);
        application.run(args);
    }
}
