package com.softserve.itacademy.service.impl;

import com.softserve.itacademy.exception.NullEntityReferenceException;
import com.softserve.itacademy.service.ToDoService;
import com.softserve.itacademy.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityNotFoundException;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class ToDoServiceTest {


    private ToDoService toDoService;

    @Autowired
    public ToDoServiceTest(ToDoService toDoService){
        this.toDoService = toDoService;
    }

    @Test
    public void createNullToDoTest(){
        assertThrows(NullEntityReferenceException.class, () -> toDoService.create(null));
    }

    @Test
    public void readNotExistingToDoTest(){
        assertThrows(EntityNotFoundException.class, () -> toDoService.readById(Long.MAX_VALUE));
    }

    @Test
    public void updateNullToDo(){
        assertThrows(NullEntityReferenceException.class, () -> toDoService.update(null));
    }

    @Test
    public void deleteNotExistingToDoTest(){
        assertThrows(EntityNotFoundException.class, () -> toDoService.delete(Long.MAX_VALUE));
    }

}
