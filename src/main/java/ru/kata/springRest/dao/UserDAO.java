package ru.kata.springRest.dao;


import ru.kata.springRest.model.User;

import java.util.List;

public interface UserDAO {
    List<User> getUsers();

    void saveUser(User user);

    User getUserById(Long id);

    void updateUser(User user);

    void deleteUser(User user);

    User findUserByName(String name);
}
