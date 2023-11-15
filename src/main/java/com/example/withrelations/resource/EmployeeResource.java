package com.example.withrelations.resource;

import com.example.withrelations.entity.Employee;
import com.example.withrelations.entity.EmployeeWithDetails;
import jakarta.ws.rs.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;

@Path("/employeeResource")
@Consumes("application/json")
@Produces("application/json")
public class EmployeeResource {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @POST
    @Path("/saveEmployee")
    public Employee saveEmployee(Employee employee) {
        String sql = "INSERT INTO employee (name, department_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, employee.getName(), employee.getDepartmentId());
        return employee;
    }

    @GET
    @Path("/getAllEmployees")
    public List<EmployeeWithDetails> getAllEmployees(
            @QueryParam("page") @DefaultValue("1") int page,
            @QueryParam("size") @DefaultValue("10") int size,
            @QueryParam("filter") @DefaultValue("") String filter,
            @QueryParam("sort") @DefaultValue("") String sort) {

        // Building the SQL query dynamically based on parameters
        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append("SELECT ")
                .append("e.id AS employee_id, ")
                .append("e.name AS employee_name, ")
                .append("d.id AS department_id, ")
                .append("d.name AS department_name, ")
                .append("c.id AS company_id, ")
                .append( "c.name AS company_name ")
                .append( "FROM employee e JOIN department d ON e.department_id = d.id JOIN company c ON d.company_id = c.id WHERE 1=1");

        // Adding filtering condition
        if (filter != null && !filter.isEmpty()) {
            sqlBuilder.append(String.format( " AND e.name ILIKE '%%%s%%'", filter));
            // Add more conditions for other fields as needed
        }

        // Adding sorting condition
        if (sort != null && !sort.isEmpty()) {
            sqlBuilder.append(String.format( " ORDER BY %s", sort));
        }

        // Adding pagination
        int offset = (page - 1) * size;
        sqlBuilder.append(String.format( " LIMIT %d OFFSET %d", size, offset));

        String finalQuery = sqlBuilder.toString();

        RowMapper<EmployeeWithDetails> rowMapper = new BeanPropertyRowMapper<>(EmployeeWithDetails.class);

        return jdbcTemplate.query(finalQuery, rowMapper);
    }

    @GET
    @Path("/getEmployee/{name}")
    public List<Employee> getEmployeesByName(@PathParam("name") String name) {
        String sql = "SELECT * FROM employee WHERE name ILIKE ?";
        return jdbcTemplate.query(sql, new Object[]{"%" + name + "%"}, new BeanPropertyRowMapper<>(Employee.class));
    }

    @GET
    @Path("/getEmployeesById/{id}")
    public List<Employee> getEmployeesById(@PathParam("id") int id) {
        String sql = "SELECT * FROM employee WHERE id = ?";
        return jdbcTemplate.query(sql, new Object[]{id}, new BeanPropertyRowMapper<>(Employee.class));
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
        String sql = "UPDATE employee SET name=?, department_id=? WHERE id=?";
        jdbcTemplate.update(sql, employee.getName(), employee.getDepartmentId(), employee.getId());

        String selectSql = "SELECT * FROM employee WHERE id = ?";
        return jdbcTemplate.queryForObject(selectSql, new Object[]{employee.getId()}, new BeanPropertyRowMapper<>(Employee.class));
    }

}