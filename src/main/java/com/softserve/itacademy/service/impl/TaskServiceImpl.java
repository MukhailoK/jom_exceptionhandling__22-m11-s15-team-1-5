package com.softserve.itacademy.service.impl;

import com.softserve.itacademy.exception.NullEntityReferenceException;
import com.softserve.itacademy.model.Task;
import com.softserve.itacademy.model.ToDo;
import com.softserve.itacademy.repository.TaskRepository;
import com.softserve.itacademy.repository.ToDoRepository;
import com.softserve.itacademy.service.TaskService;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {
    private TaskRepository taskRepository;
    private ToDoRepository todoRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Task create(Task user) {
        try {
            return taskRepository.save(user);
        } catch (InvalidDataAccessApiUsageException | IllegalArgumentException e) {
            throw new NullEntityReferenceException("Task cannot be null");
        }
    }

    @Override
    public Task readById(long id) {
        Optional<Task> optional = taskRepository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new EntityNotFoundException("Task with id " + id + " not found");
    }

    @Override
    public Task update(Task task) {
        if (task != null) {
            Task oldTask;
            try {
                oldTask = readById(task.getId());
            } catch (IllegalArgumentException e) {
                throw new NullEntityReferenceException("Task id cannot be null");
            }
            if (oldTask != null) {
                try {
                    return taskRepository.save(task);
                } catch (InvalidDataAccessApiUsageException | IllegalArgumentException e) {
                    throw new NullEntityReferenceException("Task cannot be null");
                }
            }
        }
        throw new NullEntityReferenceException("Task can`t be 'null'");
    }

    @Override
    public void delete(long id) {
        Task task = readById(id);
        if (task != null) {
            taskRepository.delete(task);
        } else {
            throw new EntityNotFoundException("Task with id " + id + " not found");
        }
    }

    @Override
    public List<Task> getAll() {
        List<Task> tasks = taskRepository.findAll();
        return tasks.isEmpty() ? new ArrayList<>() : tasks;
    }

    @Override
    public List<Task> getByTodoId(long todoId) {
        Optional<ToDo> optional = todoRepository.findById(todoId);
        if (optional.isPresent()) {
            List<Task> tasks = taskRepository.getByTodoId(todoId);
            return tasks.isEmpty() ? new ArrayList<>() : tasks;
        }
        throw new EntityNotFoundException("Todo with id " + todoId + " not found");
    }
}
