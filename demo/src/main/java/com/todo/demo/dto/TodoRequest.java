package com.todo.demo.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class TodoRequest {
    private String title;
    private String description;
    private boolean completed;
}
