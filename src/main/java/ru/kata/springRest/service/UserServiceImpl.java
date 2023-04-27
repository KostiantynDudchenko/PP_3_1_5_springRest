package ru.kata.springRest.service;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.springRest.dao.RoleDAO;
import ru.kata.springRest.dao.UserDAO;
import ru.kata.springRest.model.User;

import java.util.List;


@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserDAO userDAO;
    private final RoleDAO roleDAO;

    public UserServiceImpl(UserDAO userDAO, RoleDAO roleDAO) {
        this.userDAO = userDAO;
        this.roleDAO = roleDAO;
    }

    @Override
    public List<User> getUsers() {
        return userDAO.getUsers();
    }

    @Override
    @Transactional
    public void save(User user) {
        userDAO.saveUser(user);
    }

    @Override
    public User getUserById(Long id) {
        return userDAO.getUserById(id);
    }

    @Override
    @Transactional
    public void update(User user) {
        userDAO.updateUser(user);
    }


    @Override
    @Transactional
    public void delete(User user) {
        userDAO.deleteUser(user);
    }

    @Override
    public User findByName(String name) {
        return userDAO.findUserByName(name);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userDAO.findUserByName(username);
    }
}
