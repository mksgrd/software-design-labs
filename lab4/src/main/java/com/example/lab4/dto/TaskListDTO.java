package com.example.lab4.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class TaskListDTO {
    private long id;
    private String name;
    private List<TaskDTO> tasks;
}
