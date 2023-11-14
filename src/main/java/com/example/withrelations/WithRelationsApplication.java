package com.example.withrelations;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Arrays;

@SpringBootApplication
@RequiredArgsConstructor
public class WithRelationsApplication {

    private final JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void initDB() {
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS employee (id INT, name VARCHAR(255), dept VARCHAR(255))");
//        jdbcTemplate.batchUpdate("INSERT INTO employee (id, name, dept) VALUES (?, ?, ?)",
//                Arrays.asList(new Object[]{890, "peter", "DEV"}, new Object[]{680, "sam", "QA"}, new Object[]{143, "John", "HR"}));
    }

    public static void main(String[] args) {
        SpringApplication.run(WithRelationsApplication.class, args);
    }

}
