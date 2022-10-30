package com.example.lab4.controller;


import com.example.lab4.dto.AddTaskDTO;
import com.example.lab4.dto.AddTaskListDTO;
import com.example.lab4.dto.CompleteTaskDTO;
import com.example.lab4.dto.RemoveTaskListDTO;
import com.example.lab4.service.TaskManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class TaskManagerController {

    private final TaskManagerService service;

    @GetMapping
    public String getTaskManager(Model model) {
        model.addAttribute("addTaskDTO", new AddTaskDTO());
        model.addAttribute("completeTaskDTO", new CompleteTaskDTO());
        model.addAttribute("addTaskListDTO", new AddTaskListDTO());
        model.addAttribute("removeTaskListDTO", new RemoveTaskListDTO());
        model.addAttribute("taskLists", service.getAllTaskLists());

        return "index";
    }

    @PostMapping("/add-task")
    public String addTask(@ModelAttribute("addTaskDTO") AddTaskDTO addTaskDTO) {
        service.addTask(addTaskDTO);
        return "redirect:/";
    }

    @PostMapping("/add-list")
    public String addTaskList(@ModelAttribute("addTaskListDTO") AddTaskListDTO addTaskListDTO) {
        service.addTaskList(addTaskListDTO);
        return "redirect:/";
    }

    @PostMapping("/complete-task")
    public String completeTask(@ModelAttribute("completeTaskDTO") CompleteTaskDTO completeTaskDTO) {
        service.completeTask(completeTaskDTO);
        return "redirect:/";
    }

    @PostMapping("/remove-list")
    public String removeTaskList(@ModelAttribute("removeTaskListDTO") RemoveTaskListDTO removeTaskListDTO) {
        service.removeTaskList(removeTaskListDTO);
        return "redirect:/";
    }
}
