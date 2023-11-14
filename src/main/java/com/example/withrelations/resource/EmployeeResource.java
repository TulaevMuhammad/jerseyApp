package com.example.withrelations.resource;


import com.example.withrelations.entity.Employee;
import jakarta.ws.rs.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

@Path("/employeeResource")
@Consumes("application/json")
@Produces("application/json")
public class EmployeeResource {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @POST
    @Path("/save")
    public Employee saveEmployee(Employee employee) {
        String sql = "INSERT INTO employee (id, name, dept) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, employee.getId(), employee.getName(), employee.getDept());
        return employee;
    }

    @GET
    @Path("/getAllEmployees")
    public List<Employee> getAllEmployees() {
        String sql = "SELECT * FROM employee";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Employee.class));
    }

    @GET
    @Path("/getEmployee/{name}")
    public Employee getEmployeeByName(@PathParam("name") String name) {
        String sql = "SELECT * FROM employee WHERE name = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{name}, new BeanPropertyRowMapper<>(Employee.class));
    }

    @DELETE
    @Path("/deleteEmployee/{id}")
    public void deleteEmployee(@PathParam("id") Integer id) {
        String sql = "DELETE FROM employee WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @PUT
    @Path("/updateEmployee")
    public Employee updateEmployee(Employee employee) {
        String sql = "UPDATE employee SET name=?, dept=? WHERE id=  ?";
        jdbcTemplate.update(sql, employee.getName(), employee.getDept(), employee.getId());

        // Assuming you want to return the updated employee, you can fetch it from the database
        String selectSql = "SELECT * FROM employee WHERE id = ?";
        return jdbcTemplate.queryForObject(selectSql, new Object[]{employee.getId()}, new BeanPropertyRowMapper<>(Employee.class));
    }

}
