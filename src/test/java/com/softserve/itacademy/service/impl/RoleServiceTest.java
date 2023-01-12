package com.softserve.itacademy.service.impl;

import com.softserve.itacademy.exception.NullEntityReferenceException;
import com.softserve.itacademy.service.RoleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityNotFoundException;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class RoleServiceTest {
    private RoleService roleService;

    @Autowired
    public RoleServiceTest(RoleService roleService){
        this.roleService = roleService;
    }

    @Test
    public void createNullRoleTest(){
        assertThrows(NullEntityReferenceException.class, () -> roleService.create(null));
    }

    @Test
    public void readNotExistingRoleTest(){
        assertThrows(EntityNotFoundException.class, () -> roleService.readById(Long.MAX_VALUE));
    }

    @Test
    public void updateNullRole(){
        assertThrows(NullEntityReferenceException.class, () -> roleService.update(null));
    }

    @Test
    public void deleteNotExistingRoleTest(){
        assertThrows(EntityNotFoundException.class, () -> roleService.delete(Long.MAX_VALUE));
    }
}
