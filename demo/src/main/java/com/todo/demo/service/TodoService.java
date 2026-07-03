package com.todo.demo.service;

import com.todo.demo.dto.TodoRequest;
import com.todo.demo.dto.TodoResponse;
import com.todo.demo.entity.Todo;
import com.todo.demo.entity.User;
import com.todo.demo.mapper.TodoMapper;
import com.todo.demo.repository.TodoRepository;
import com.todo.demo.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;
    private final UserRepository userRepository;

    public List<TodoResponse> getAllTodos(){
        return  todoRepository.findAll().stream().map(TodoMapper::toResponse).toList();
    }

    public List<TodoResponse> getMyTodos(){
        String currentEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        return todoRepository.findByUserEmail(currentEmail).stream().map(TodoMapper::toResponse).toList();
    }

    public TodoResponse createTodo(TodoRequest request) {
        String currentEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByEmail(currentEmail).orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));
        Todo todo = TodoMapper.toEntity(request);
        todo.setUser(currentUser);
        Todo savedTodo = todoRepository.save(todo);
        return TodoMapper.toResponse(todo);
    }

    public Todo getTodoById(Long id) {
        return todoRepository.findById(id).orElseThrow();
    }
    public void deleteTodo(Long id) {
        if (!todoRepository.existsById(id)) {
            throw new RuntimeException("Silinmek istenen Todo bulunamadı! ID: " + id);
        }
        todoRepository.deleteById(id);
    }

    public Todo updateTodo(Long id, Todo todoDetails) {
        Todo existingTodo = todoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Todo bulunamadı! ID: " + id));

        existingTodo.setTitle(todoDetails.getTitle());
        existingTodo.setDescription(todoDetails.getDescription());
        existingTodo.setCompleted(todoDetails.isCompleted());

        return todoRepository.save(existingTodo);
    }
}
