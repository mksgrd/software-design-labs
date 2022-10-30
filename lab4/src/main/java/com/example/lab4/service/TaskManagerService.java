package com.example.lab4.service;

import com.example.lab4.dto.*;
import com.example.lab4.model.Task;
import com.example.lab4.model.TaskList;
import com.example.lab4.repository.TaskListRepository;
import com.example.lab4.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.StreamSupport;

@RequiredArgsConstructor
@Service
public class TaskManagerService {
    private final TaskListRepository taskListRepository;
    private final TaskRepository taskRepository;

    public List<TaskListDTO> getAllTaskLists() {
        return StreamSupport.stream(taskListRepository.findAll().spliterator(), false)
                .map(TaskManagerService::toDTO)
                .toList();
    }

    public void addTask(AddTaskDTO addTaskDTO) {
        taskListRepository.findById(addTaskDTO.getListId()).ifPresent(taskList -> {
            Task task = new Task();
            task.setName(addTaskDTO.getName());
            taskList.getTasks().add(task);
            taskListRepository.save(taskList);
        });
    }


    public void addTaskList(AddTaskListDTO addTaskListDTO) {
        TaskList taskList = new TaskList();
        taskList.setName(addTaskListDTO.getName());
        taskListRepository.save(taskList);
    }

    public void completeTask(CompleteTaskDTO completeTaskDTO) {
        taskRepository.findById(completeTaskDTO.getId()).ifPresent(task -> {
            task.setCompleted(true);
            taskRepository.save(task);
        });
    }

    public void removeTaskList(RemoveTaskListDTO removeTaskListDTO) {
        if (taskListRepository.existsById(removeTaskListDTO.getId())) {
            taskListRepository.deleteById(removeTaskListDTO.getId());
        }
    }

    private static TaskDTO toDTO(Task task) {
        return new TaskDTO(task.getId(), task.getName(), task.isCompleted());
    }

    private static TaskListDTO toDTO(TaskList taskList) {
        return new TaskListDTO(
                taskList.getId(),
                taskList.getName(),
                taskList.getTasks().stream().map(TaskManagerService::toDTO).toList()
        );
    }
}
