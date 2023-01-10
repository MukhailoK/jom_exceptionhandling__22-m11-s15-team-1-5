package com.softserve.itacademy.service.impl;

import com.softserve.itacademy.exception.NullEntityReferenceException;
import com.softserve.itacademy.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityNotFoundException;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class UserServiceImplTest {

    @Autowired
    private UserService underTest;

    @Test
    void createWithNull() {
        assertThrows(NullEntityReferenceException.class, () -> underTest.create(null));
    }

    @Test
    void readByNullId() {
        assertThrows(EntityNotFoundException.class, () -> underTest.readById(100000000L));
    }

    @Test
    void update() {
        assertThrows(NullEntityReferenceException.class, () -> underTest.update(null));
    }

    @Test
    void delete() {
        assertThrows(EntityNotFoundException.class, () -> underTest.delete(100000000L));
    }
}