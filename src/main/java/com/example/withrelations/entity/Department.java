package com.example.withrelations.entity;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Department {
    private int id;
    private String name;
    private int companyId;
}
