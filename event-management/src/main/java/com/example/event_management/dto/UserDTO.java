package com.example.event_management.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserDTO {
    private Long id;
    private String email;
    private String fullName;
    private int age;
    private String address;
    private LocalDateTime dateBirth;
}
