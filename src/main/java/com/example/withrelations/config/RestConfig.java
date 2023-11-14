package com.example.withrelations.config;

import com.example.withrelations.resource.EmployeeResource;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

@Component
public class RestConfig extends ResourceConfig {

    public RestConfig(Class<?>... classes) {
        register(EmployeeResource.class);
    }
}
