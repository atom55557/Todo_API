package com.todo.demo.controller;

import com.todo.demo.dto.TodoRequest;
import com.todo.demo.dto.TodoResponse;
import com.todo.demo.entity.Todo;
import com.todo.demo.service.TodoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/todos")

public class TodoController {

    private final TodoService todoService;

    @GetMapping
    public ResponseEntity<List<TodoResponse>> getMyTodos(){
        return ResponseEntity.ok(todoService.getMyTodos());
    }

    @GetMapping("/all")
    public ResponseEntity<List<TodoResponse>> getAllTodos(){
        return ResponseEntity.ok(todoService.getAllTodos());
    }


    @PostMapping
    public ResponseEntity<TodoResponse> create(@RequestBody TodoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(todoService.createTodo(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TodoResponse> update(@PathVariable Long id, @RequestBody TodoRequest request) {
        return ResponseEntity.ok(todoService.updateTodo(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        todoService.deleteTodo(id);
        return ResponseEntity.ok("Todo başarıyla silindi. ID: " + id);
    }
}
