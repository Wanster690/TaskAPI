package org.example;

import jakarta.persistence.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Optional;

@SpringBootApplication
@RestController
public class TaskAPI {

    @Autowired
    private TaskRepository taskRepository;

    @Entity
    public static class Task {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String title;
        private String description;

        public Task(String title, String description) {
            this.title = title;
            this.description = description;
        }

        public Task() {
        }

        public Long getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }

    // Получение задания по id
    @GetMapping("/tasks/{taskId}")
    public Task getTask(@PathVariable Long taskId) {
        return taskRepository.findById(taskId).orElse(null);
    }

    // Получение всех заданий
    @GetMapping("/tasks")
    public List<Task> getTasks() {
        return taskRepository.findAll();
    }

    // Добавление нового задания
    @PostMapping("/tasks")
    public Task createTask(@RequestBody Task newTask) {
        return taskRepository.save(newTask);
    }

    // Удаление задания по id
    @DeleteMapping("/tasks/{taskId}")
    public String deleteTaskById(@PathVariable Long taskId) {
        Optional<Task> taskToDelete = taskRepository.findById(taskId);

        if (taskToDelete.isPresent()) {
            Task task = taskToDelete.get();
            taskRepository.deleteById(taskId);
            return "Удалена задача с id: " + task.getId() + " и названием: " + task.getTitle();
        } else {
            return "Задача с id " + taskId + " не найдена.";
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(TaskAPI.class, args);
    }
}