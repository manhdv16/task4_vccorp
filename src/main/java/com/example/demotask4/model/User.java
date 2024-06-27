package com.example.demotask4.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class User {
    private String name;
    private String email;
    private String phone;
    private int age;
    private String address;
}
