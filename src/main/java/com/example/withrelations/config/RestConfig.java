package com.example.withrelations.config;

import com.example.withrelations.entity.Department;
import com.example.withrelations.resource.CompanyResource;
import com.example.withrelations.resource.EmployeeResource;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

@Component
public class RestConfig extends ResourceConfig {

    public RestConfig(Class<?>... classes) {
        register(EmployeeResource.class);
        register(CompanyResource.class);
        register(Department.class);
    }

}
