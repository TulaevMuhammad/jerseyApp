package com.example.withrelations.entity;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Employee {
    private int id;
    private String name;
    private String dept;
}
