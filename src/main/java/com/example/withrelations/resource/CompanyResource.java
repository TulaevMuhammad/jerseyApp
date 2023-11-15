package com.example.withrelations.resource;

import com.example.withrelations.entity.Company;
import jakarta.ws.rs.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

@Path("/api/v1/company")
@Consumes("application/json")
@Produces("application/json")
@RequiredArgsConstructor
public class CompanyResource {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @POST
    @Path("/saveCompany")
    public Company saveCompany(Company company) {
        String sql = "INSERT INTO company (name) VALUES (?)";
        jdbcTemplate.update(sql, company.getName());
        return company;
    }

    @GET
    @Path("/getAllCompanies")
    public List<Company> getAllCompanies() {
        String sql = "SELECT * FROM company";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Company.class));
    }

    @PUT
    @Path("/updateCompany")
    public Company updateCompany(Company company) {
        String sql = "UPDATE company SET name=? WHERE id=?";
        jdbcTemplate.update(sql, company.getName(), company.getId());

        // Assuming you want to return the updated company, you can fetch it from the database
        String selectSql = "SELECT * FROM company WHERE id = ?";
        return jdbcTemplate.queryForObject(selectSql, new Object[]{company.getId()}, new BeanPropertyRowMapper<>(Company.class));
    }

    @GET
    @Path("/getCompany/{id}")
    public Company getCompanyById(@PathParam("id") Integer id) {
        String sql = "SELECT * FROM company WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new BeanPropertyRowMapper<>(Company.class));
    }


}
