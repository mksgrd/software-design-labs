package com.example.lab4.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TaskDTO {
    private long id;
    private String name;
    private boolean completed;
}
