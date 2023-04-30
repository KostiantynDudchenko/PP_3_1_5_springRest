package ru.kata.springRest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.springRest.model.Role;
import ru.kata.springRest.model.User;
import ru.kata.springRest.service.RoleService;
import ru.kata.springRest.service.UserService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class AdminRestController {
    private final UserService userService;
    private final RoleService roleService;

    public AdminRestController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/admin")
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userService.getUsers(), HttpStatus.OK);
    }

    @PostMapping("/users")
    public ResponseEntity<User> addNewUser(@RequestBody User user) {
        userService.save(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/admin")
    public ResponseEntity<User> update(@RequestBody User editedUser,
                         @RequestParam(value = "editedRoles", required = false) String[] editedRoles) {
        if (editedRoles != null) {
            Set<Role> roles = new HashSet<>();
            for (String elemArrSelectedRoles : editedRoles) {
                roles.add(roleService.getRoleByName(elemArrSelectedRoles));
            }
            editedUser.setRoles(roles);
        }
        userService.update(editedUser);
        return new ResponseEntity<>(editedUser, HttpStatus.OK);
    }

    // Удаление юзера
    @DeleteMapping("/admin/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        if (user == null) {
            return new ResponseEntity<>("User with ID=" + id + " was not found", HttpStatus.NOT_FOUND);
        }
        userService.delete(user);
        return new ResponseEntity<>("User with ID=" + id + " was deleted", HttpStatus.OK);
    }

    @GetMapping("/admin/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return user != null
                ? new ResponseEntity<>(user, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/roles")
    public ResponseEntity<List<Role>> getRoles() {
        return new ResponseEntity<>(roleService.getAllRoles(), HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<User> getAuthenticatedUser(@AuthenticationPrincipal User principal) {
        User user = userService.getUserById(principal.getId());
        return user != null
                ? new ResponseEntity<>(user, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
