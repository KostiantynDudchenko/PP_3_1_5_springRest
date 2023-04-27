package ru.kata.springRest.service;


import ru.kata.springRest.model.User;

import java.util.List;

public interface UserService {
    List<User> getUsers();

    void save(User user);

    User getUserById(Long id);

    void update(User user);

    void delete(User user);

    User findByName(String name);
}
