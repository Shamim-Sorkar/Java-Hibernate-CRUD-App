package com.shamim.Hibernate_CRUD.service;

import com.shamim.Hibernate_CRUD.entity.User;
import com.shamim.Hibernate_CRUD.exception.DatabaseOperationException;
import com.shamim.Hibernate_CRUD.exception.UserNotFoundException;
import com.shamim.Hibernate_CRUD.repository.UserRepository;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User saveUser(User user) {
        try {
            return userRepository.save(user);
        } catch (HibernateException e) {
            throw new DatabaseOperationException("Failed to save user to database");
        }
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        try {
            return userRepository.findById(id);
        }
        catch (UserNotFoundException e) {
            throw e;
        }
    }

    public void deleteUser(User user) {
        try {
            userRepository.delete(user);
        } catch (HibernateException e) {
            throw new DatabaseOperationException("Failed to delete user from database");
        }
    }

    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }
}
