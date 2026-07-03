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
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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

        Todo todo = todoRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Silinmek istenen todo bulunamadı ID:"+ id));

        String currentEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        boolean isAdmin = SecurityContextHolder.getContext().getAuthentication().getAuthorities()
                .contains(new SimpleGrantedAuthority("ROLE_ADMIN"));

        if(!isAdmin && !todo.getUser().getEmail().equals(currentEmail)){
            throw new RuntimeException("Bu işlem için yetkiniz yok! Yalnızca kendi notlarınızı silebilirsiniz.");
        }

        todoRepository.deleteById(id);
    }

    public TodoResponse updateTodo(Long id, TodoRequest request) {
        Todo existingTodo = todoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Todo bulunamadı! ID: " + id));

        String currentEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean isAdmin = SecurityContextHolder.getContext().getAuthentication().getAuthorities()
                .contains(new SimpleGrantedAuthority("ROLE_ADMIN"));

        if(!isAdmin && !existingTodo.getUser().getEmail().equals(currentEmail)){
            throw new RuntimeException("Bu işlem için yetkiniz yok! Yalnızca kendi notlarınızı güncelleyebilirsiniz.");
        }

        existingTodo.setTitle(request.getTitle());
        existingTodo.setDescription(request.getDescription());
        existingTodo.setCompleted(request.isCompleted());
        Todo updatedTodo = todoRepository.save(existingTodo);
        return TodoMapper.toResponse(updatedTodo);
    }
}
