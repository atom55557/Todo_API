package com.todo.demo.mapper;

import com.todo.demo.dto.TodoRequest;
import com.todo.demo.dto.TodoResponse;
import com.todo.demo.entity.Todo;

public class TodoMapper {

    public static Todo toEntity(TodoRequest request){
        if(request==null){
            return null;
        }
        Todo todo= new Todo();
        todo.setTitle(request.getTitle());
        todo.setDescription(request.getDescription());
        todo.setCompleted(request.isCompleted());
        return todo;
    }

    public static TodoResponse toResponse(Todo todo){
        if (todo==null){
            return null;
        }
        TodoResponse response = new TodoResponse();
        response.setId(todo.getId());
        response.setTitle(todo.getTitle());
        response.setDescription(todo.getDescription());
        response.setCompleted(todo.isCompleted());

        return response;
    }
}
