package com.example.lab4database.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Patient {
    private int id;
    private String name;
    private String mobile;
    private String contact;
    private String address;
    private String email;
}
