package com.example.withrelations.resource;

import com.example.withrelations.entity.Department;
import jakarta.ws.rs.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
@Path("api/v1/department")
@Consumes("application/json")
@Produces("application/json")
public class DepartmentResource {


    @Autowired
    private JdbcTemplate jdbcTemplate;
    @POST
    @Path("/saveDepartment")
    public Department saveDepartment(Department department) {
        String sql = "INSERT INTO department (name, company_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, department.getName(), department.getCompanyId());
        return department;
    }

    @GET
    @Path("/getAllDepartments")
    public List<Department> getAllDepartments() {
        String sql = "SELECT * FROM department";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Department.class));
    }

    @PUT
    @Path("/updateDepartment")
    public Department updateDepartment(Department department) {
        String sql = "UPDATE department SET name=?, company_id=? WHERE id=?";
        jdbcTemplate.update(sql, department.getName(), department.getCompanyId(), department.getId());

        // Assuming you want to return the updated department, you can fetch it from the database
        String selectSql = "SELECT * FROM department WHERE id = ?";
        return jdbcTemplate.queryForObject(selectSql, new Object[]{department.getId()}, new BeanPropertyRowMapper<>(Department.class));
    }

    @GET
    @Path("/getDepartment/{id}")
    public Department getDepartmentById(@PathParam("id") Integer id) {
        String sql = "SELECT * FROM department WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new BeanPropertyRowMapper<>(Department.class));
    }

}
