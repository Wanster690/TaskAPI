package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@RestController
public class TaskAPI {

    public static class Task {
        private int id;
        private String title;
        private String description;


        public Task(int id, String title, String description) {
            this.id = id;
            this.title = title;
            this.description = description;

        }


        public void setId(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public String getTitle(){return title;}

        public String getDescription(){return description;}

    }


    private List<Task> tasks = new ArrayList<>();

    public TaskAPI() {
        tasks.add(new Task(1, "Задание №1", "Описание задания №1"));
        tasks.add(new Task(2, "Задание №2", "Описание задания №2"));
        tasks.add(new Task(3, "Задание №3", "Описание задания №3"));

    }

    // Получение задания по id
    @GetMapping("/tasks/{taskId}")
    public Task getTask(@PathVariable int taskId) {
        return tasks.stream()
                .filter(task -> task.getId() == taskId)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Задание не найдено"));
    }

    // Получение всех заданий
    @GetMapping("/tasks")
    public List<Task> getTasks() {
        return tasks;
    }

    //добавление задания
    @PostMapping("/tasks")
    public Task createTask(@RequestBody Task newTask) {
        newTask.setId(tasks.size() + 1);
        tasks.add(newTask);
        return newTask;
    }

    public static void main(String[] args) {
        SpringApplication.run(TaskAPI.class, args);
    }
}

