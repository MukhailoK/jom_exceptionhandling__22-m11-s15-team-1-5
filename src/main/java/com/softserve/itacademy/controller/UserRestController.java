package com.softserve.itacademy.controller;

import com.softserve.itacademy.dto.UserDto;
import com.softserve.itacademy.dto.UserTransformer;
import com.softserve.itacademy.model.User;
import com.softserve.itacademy.repository.UserRepository;
import com.softserve.itacademy.service.UserService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users-rest")
public class UserRestController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final UserTransformer userTransformer;

    public UserRestController(UserService userService,
                              UserRepository userRepository, UserTransformer userTransformer) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.userTransformer = userTransformer;
    }

    @GetMapping(value = "/users")
    public List<UserDto> getUsers() {
        return userService.getAll().stream()
                .map(userTransformer::convertToDto).collect(Collectors.toList());
    }

    @PostMapping("/user/create")
    public UserDto create(@RequestBody UserDto userDto, BindingResult bindingResult) {
        User newUser = userTransformer.convertToInsertEntity(userDto);
        return userTransformer.convertToDto(userService.create(newUser));
    }

    @GetMapping(value = "/user/{id}/")
    public UserDto getUser(@PathVariable long id) {
        return userTransformer.convertToDto(userService.readById(id));
    }

    @PutMapping("/user/{id}/")
    public UserDto updateUserById(@PathVariable long id, @RequestBody UserDto userDto, BindingResult bindingResult) {
        User oldUser = userTransformer.convertToUpdateEntity(userDto, bindingResult);
        return userTransformer.convertToDto(userService.update(oldUser));
    }
}
