package com.example.event_management.dto;


import com.example.event_management.response.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDTO<T> {
    private Status status;
    private String message;
    private T data;

    @CreatedDate
    private Date timestamp;
}
