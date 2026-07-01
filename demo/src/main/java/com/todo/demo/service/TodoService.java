package com.todo.demo.service;

import com.todo.demo.entity.Todo;
import com.todo.demo.repository.TodoRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;

    public List<Todo> getAllTodos(){
        return  todoRepository.findAll();
    }

    public Todo createTodo(Todo todo) {
        return todoRepository.save(todo);
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
