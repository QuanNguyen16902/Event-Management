package com.example.event_management.mapper;

import com.example.event_management.dto.UserDTO;
import com.example.event_management.entity.User;

public class UserMapper {
    public static UserDTO toDto(User user){
        if(user == null){
            return null;
        }
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setEmail(user.getEmail());
        userDTO.setFullName(user.getFullName());
        userDTO.setAge(user.getAge());
        userDTO.setAddress(user.getAddress());
        userDTO.setDateBirth(user.getDateBirth());
        return userDTO;
    }

    public static User toEntity(UserDTO userDTO){
        if(userDTO == null){
            return null;
        }
        User user = new User();
        user.setId(userDTO.getId());
        user.setEmail(userDTO.getEmail());
        user.setFullName(userDTO.getFullName());
        user.setAge(userDTO.getAge());
        user.setAddress(userDTO.getAddress());
        user.setDateBirth(userDTO.getDateBirth());
        return user;
    }
}
