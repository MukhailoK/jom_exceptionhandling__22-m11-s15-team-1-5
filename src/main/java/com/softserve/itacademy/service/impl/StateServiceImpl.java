package com.softserve.itacademy.service.impl;

import com.softserve.itacademy.exception.NullEntityReferenceException;
import com.softserve.itacademy.model.State;
import com.softserve.itacademy.repository.StateRepository;
import com.softserve.itacademy.service.StateService;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StateServiceImpl implements StateService {
    private StateRepository stateRepository;

    public StateServiceImpl(StateRepository stateRepository) {
        this.stateRepository = stateRepository;
    }

    @Override
    public State create(State state) {
        try {
            return stateRepository.save(state);
        } catch (InvalidDataAccessApiUsageException | IllegalArgumentException e) {
            throw new NullEntityReferenceException("State cannot be null");
        }
    }


    @Override
    public State readById(long id) {
        Optional<State> optional = stateRepository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new EntityNotFoundException("Task with id " + id + " not found");
    }

    @Override
    public State update(State state) {
        if (state != null) {
            State oldState;
            try {
                oldState = readById(state.getId());
            } catch (IllegalArgumentException e) {
                throw new NullEntityReferenceException("State id cannot be null");
            }
            if (oldState != null) {
                try {
                    return stateRepository.save(state);
                } catch (InvalidDataAccessApiUsageException | IllegalArgumentException e) {
                    throw new NullEntityReferenceException("Sate cannot be null");
                }
            }
        }
        throw new NullEntityReferenceException("State can`t be 'null'");
    }

    @Override
    public void delete(long id) {
        State state = readById(id);
        if (state != null) {
            stateRepository.delete(state);
        } else {
            throw new EntityNotFoundException("Sate with id " + id + " not found");
        }
    }

    @Override
    public State getByName(String name) {
        Optional<State> optional = Optional.ofNullable(stateRepository.getByName(name));
        return optional.get();
    }

    @Override
    public List<State> getAll() {
        List<State> states = stateRepository.getAll();
        return states.isEmpty() ? new ArrayList<>() : states;
    }
}
