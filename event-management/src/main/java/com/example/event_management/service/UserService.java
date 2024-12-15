package com.example.event_management.service;

import com.example.event_management.dto.UserDTO;
import com.example.event_management.entity.User;
import com.example.event_management.exception.UserNotFoundException;
import com.example.event_management.mapper.UserMapper;
import com.example.event_management.repo.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public List<UserDTO> getAllUser(){
        return userRepository.findAll().stream()
                .map(UserMapper::toDto)
                .collect(Collectors.toList());
    }
    public UserDTO getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(UserMapper::toDto)
                .orElseThrow(() -> new UserNotFoundException("Người dùng không tồn tại với ID: " + id));
    }
//    public Optional<UserDTO> getOneUser(Long id){
//        Optional<UserDTO> userDTO = userRepository.findById(id)
//                .map(UserMapper::toDto);
//        if(userDTO.isEmpty()){
//            throw new UserNotFoundException("Người dùng không tồn tại với ID: " + id);
//        }
//        return userDTO;
//    }
    public boolean isExistUser(String email){
        return userRepository.existsByEmail(email);
    }
    public UserDTO createUser(UserDTO userDTO){
        User user = UserMapper.toEntity(userDTO);
        User savedUser = userRepository.save(user);
        return UserMapper.toDto(savedUser);
    }
    public UserDTO updateUser(Long id, UserDTO userDTO){
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isPresent()){
            User user = optionalUser.get();
            user.setEmail(userDTO.getEmail());
            user.setFullName(userDTO.getFullName());
            user.setAddress(userDTO.getAddress());
            user.setAge(userDTO.getAge());
            user.setDateBirth(userDTO.getDateBirth());

            User updatedUser = userRepository.save(user);
            return UserMapper.toDto(updatedUser);
        }
        return null;
    }
    public boolean deleteUser(Long id){
        if(userRepository.existsById(id)){
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
