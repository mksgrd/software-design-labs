package com.example.lab4.repository;

import com.example.lab4.model.TaskList;
import org.springframework.data.repository.CrudRepository;

public interface TaskListRepository extends CrudRepository<TaskList, Long> {
}
