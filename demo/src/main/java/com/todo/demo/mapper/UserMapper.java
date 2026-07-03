package com.todo.demo.mapper;

import com.todo.demo.dto.RegisterRequest;
import com.todo.demo.entity.User;

public class UserMapper {

    public static User registerRequestToUser(RegisterRequest request){
        if(request==null){
            return null;
        }

        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());

        return user;
    }
}
