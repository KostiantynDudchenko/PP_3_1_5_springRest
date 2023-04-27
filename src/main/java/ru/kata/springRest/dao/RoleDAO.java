package ru.kata.springRest.dao;

import ru.kata.springRest.model.Role;

import java.util.List;

public interface RoleDAO {
    void addRole(Role role);

    void updateRole(Role role);

    void removeRoleById(long id);

    List<Role> getAllRoles();

    Role getRoleByName(String name);

}
