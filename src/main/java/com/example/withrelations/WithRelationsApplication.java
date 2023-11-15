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
        jdbcTemplate.execute(
                "CREATE TABLE IF NOT EXISTS company (id SERIAL not null, name VARCHAR(255) not null, PRIMARY KEY (id))"
        );
        jdbcTemplate.execute(
                "CREATE TABLE IF NOT EXISTS department (id SERIAL not null, name VARCHAR(255) not null, company_id INTEGER not null, PRIMARY KEY (id), FOREIGN KEY (company_id) REFERENCES company(id))"
        );
        jdbcTemplate.execute(
                "CREATE TABLE IF NOT EXISTS employee (id SERIAL not null, name VARCHAR(255) not null, department_id INTEGER not null, PRIMARY KEY (id), FOREIGN KEY (department_id) REFERENCES department(id))"
        );


        jdbcTemplate.update("INSERT INTO company (name) VALUES ('ABC Corp')");
        jdbcTemplate.update("INSERT INTO department (name, company_id) VALUES ('IT Department', 1)");
        jdbcTemplate.update("INSERT INTO employee (name, department_id) VALUES ('John Doe', 1)");
        jdbcTemplate.update("INSERT INTO employee (name, department_id) VALUES ('Jane Smith', 1)");
    }

    public static void main(String[] args) {
        SpringApplication.run(WithRelationsApplication.class, args);
    }

}
