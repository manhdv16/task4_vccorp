package com.example.demotask4.controller;

import com.example.demotask4.dao.UserDao;
import com.example.demotask4.dao.impl.UserDaoImpl;
import com.example.demotask4.model.User;
import com.example.demotask4.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1")
public class UserController {
    private UserDao userDao = new UserDaoImpl();

    @GetMapping("users/{id}")
    public ResponseEntity<?> getUserById(int id) {
        User u = new User();
        u = userDao.findUserById(id);
        if(u == null) {
            return ResponseEntity.ok("User not found");
        }
        ApiResponse<User> response = new ApiResponse<>(1, 200, "User found", u);
        return ResponseEntity.ok(response);
    }
    @GetMapping("users/name")
    public ResponseEntity<?> getUserByName(@RequestParam String name) {
        User u = userDao.findUserByName(name);
        if(u == null) {
            return ResponseEntity.ok("User not found");
        }
        ApiResponse<User> response = new ApiResponse<>(1, 200, "User found", u);
        return ResponseEntity.ok(response);
    }
    @GetMapping("users/address")
    public ResponseEntity<?> getUserByAddress(@RequestParam String address) {
        List<User> list = userDao.findUserByAddress(address);
        if(list.size() == 0) {
            return ResponseEntity.ok("User not found");
        }
        ApiResponse<List<User>> response = new ApiResponse<>(1, 200, "User found", list);
        return ResponseEntity.ok(response);
    }
    @GetMapping("users/sort")
    public ResponseEntity<?> sortUsersByName(@RequestParam String name) {
        List<User> users = userDao.sortUsersByName();
        return ResponseEntity.ok("Users sorted by name");
    }
    @PostMapping("users")
    public ResponseEntity<?> saveUser(@RequestBody User user) {
        String mess = userDao.validateUser(user);
        if(!mess.equals("true")) return ResponseEntity.ok(mess);
        String message = userDao.insertUser(user);
        return ResponseEntity.ok(message);
    }
    @PutMapping("users/{id}")
    public ResponseEntity<?> updateUser(@PathVariable int id, @RequestBody User user) {
        userDao.updateUser(user, id);
        return ResponseEntity.ok(user);
    }
    @DeleteMapping("users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable int id) {
        userDao.deleteUserById(id);
        return ResponseEntity.ok("User deleted successfully");
    }
}
