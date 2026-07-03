package com.todo.demo.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class TodoResponse {

    private Long id;
    private String title;
    private String description;
    private boolean completed;
}
