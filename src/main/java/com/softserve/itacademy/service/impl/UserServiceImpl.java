package com.softserve.itacademy.service.impl;

import com.softserve.itacademy.exception.NullEntityReferenceException;
import com.softserve.itacademy.model.User;
import com.softserve.itacademy.repository.UserRepository;
import com.softserve.itacademy.service.UserService;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User create(User user) {
        try {
            return userRepository.save(user);
        } catch (InvalidDataAccessApiUsageException | IllegalArgumentException e) {
            throw new NullEntityReferenceException("User cannot be null");
        }
    }

    @Override
    public User readById(long id) {
        Optional<User> optional = userRepository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new EntityNotFoundException("User with id " + id + " not found");
    }

    @Override
    public User update(User user) {
        if (user != null) {
            User oldUser;
            try {
                oldUser = readById(user.getId());
            } catch (IllegalArgumentException e) {
                throw new NullEntityReferenceException("User id cannot be null");
            }
            if (oldUser != null) {
                try {
                    return userRepository.save(user);
                } catch (InvalidDataAccessApiUsageException | IllegalArgumentException e) {
                    throw new NullEntityReferenceException("User cannot be null");
                }
            }
        }
        throw new NullEntityReferenceException("User can`t be 'null'");
    }

    @Override
    public void delete(long id) {
        User user = readById(id);
        if (user != null) {
            userRepository.delete(user);
        } else {
            throw new EntityNotFoundException("User with id " + id + " not found");
        }
    }

    @Override
    public List<User> getAll() {
        List<User> users = userRepository.findAll();
        return users.isEmpty() ? new ArrayList<>() : users;
    }

}
