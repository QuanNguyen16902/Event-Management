package com.example.event_management.controller;

import com.example.event_management.dto.ResponseDTO;
import com.example.event_management.dto.UserDTO;
import com.example.event_management.exception.UserNotFoundException;
import com.example.event_management.response.Message;
import com.example.event_management.response.Status;
import com.example.event_management.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    public ResponseDTO<UserDTO> buildResponse(Status status, String msg, UserDTO userDTO, Date date){
        return new ResponseDTO<>(
               status, msg, userDTO, date);
    }
    private ResponseEntity<ResponseDTO<UserDTO>> handleResponse(Status status, String message, UserDTO data, HttpStatus httpStatus) {
        ResponseDTO<UserDTO> responseDTO = buildResponse(status, message, data, new Date());
        return ResponseEntity.status(httpStatus).body(responseDTO);
    }
    @GetMapping("")
    public ResponseEntity<ResponseDTO<List<UserDTO>>> getAllUsers(){
        List<UserDTO> userDtos = userService.getAllUser();

        if(userDtos.isEmpty()){
            ResponseDTO<List<UserDTO>> responseDTO = new ResponseDTO<>(
                    Status.FAILED, Message.USER_READ_SUCCESS, userDtos, new Date()
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
        }
        ResponseDTO<List<UserDTO>> responseDTO = new ResponseDTO<>(
                Status.SUCCESS, Message.USER_READ_SUCCESS, userDtos, new Date()
        );
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<UserDTO>> getUserById(@PathVariable Long id){
        try{
            UserDTO userDTO = userService.getUserById(id);

            if (userDTO != null){
               return handleResponse(Status.SUCCESS, Message.USER_READ_SUCCESS, userDTO, HttpStatus.OK);
            }else {
               return handleResponse(Status.ERROR, Message.USER_READ_FAILURE, null, HttpStatus.BAD_REQUEST);
            }
        }catch (UserNotFoundException e){
            return handleResponse(Status.ERROR, Message.USER_NOT_FOUND, null, HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping("")
    public ResponseEntity<ResponseDTO<UserDTO>> createUser(@RequestBody UserDTO userDTO){
        if(userService.isExistUser(userDTO.getEmail())){
            return handleResponse(Status.FAILED, Message.USER_ALREADY_EXIST, null, HttpStatus.BAD_REQUEST);
        }
        UserDTO newUser = userService.createUser(userDTO);
        return handleResponse(Status.SUCCESS, Message.USER_CREATE_SUCCESS, newUser, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<UserDTO>> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO){
        try{
            UserDTO updatedUser = userService.getUserById(id);

            if (!userDTO.getEmail().equals(updatedUser.getEmail())
                    && userService.isExistUser(userDTO.getEmail())) {
                return handleResponse(Status.FAILED, Message.EMAIL_ALREADY_EXIST, null, HttpStatus.BAD_REQUEST);
            } else {
                UserDTO savedUser = userService.updateUser(id, userDTO);
                return handleResponse(Status.SUCCESS, Message.USER_UPDATED_SUCCESS, savedUser, HttpStatus.OK);
            }

        }catch (Exception e){
            return handleResponse(Status.ERROR, Message.USER_UPDATED_FAILURE, null, HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<UserDTO>> deletedUser(@PathVariable Long id){
        try {
            boolean deletedUser = userService.deleteUser(id);
            if (deletedUser) {
                return handleResponse(Status.SUCCESS, Message.USER_DELETED_SUCCESS, null, HttpStatus.OK);
            } else {
                return handleResponse(Status.FAILED, Message.USER_NOT_FOUND, null, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            // Handle unexpected exceptions
            return handleResponse(Status.ERROR, "Có lỗi xảy ra khi xóa người dùng", null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
