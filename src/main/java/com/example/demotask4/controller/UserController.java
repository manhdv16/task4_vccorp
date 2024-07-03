package com.example.demotask4.controller;

import com.example.demotask4.dao.UserDao;
import com.example.demotask4.dao.impl.UserDaoImpl;
import com.example.demotask4.model.User;
import com.example.demotask4.response.ApiResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1")
public class UserController {
    private UserDao userDao = new UserDaoImpl();
    private Logger LOGGER = LogManager.getLogger(UserController.class);

    @GetMapping("users/{id}")
    public ResponseEntity<?> getUserById(@PathVariable int id) {
        User u = userDao.findUserById(id);
        if(u == null) {
            LOGGER.error("User not found");
            ApiResponse<String> response = new ApiResponse<>(0, 404, "User not found", null);
            return ResponseEntity.ok(response);
        }
        ApiResponse<User> response = new ApiResponse<>(1, 200, "User found", u);
        LOGGER.info("User found", u);
        return ResponseEntity.ok(response);
    }
    @GetMapping("users/name")
    public ResponseEntity<?> getUserByName(@RequestParam String name) {
        User u = userDao.findUserByName(name);
        if(u == null) {
            LOGGER.error("User not found");
            ApiResponse<String> response = new ApiResponse<>(0, 404, "User not found", null);
            return ResponseEntity.ok(response);
        }
        ApiResponse<User> response = new ApiResponse<>(1, 200, "User found", u);
        LOGGER.info("User found", u);
        return ResponseEntity.ok(response);
    }
    @GetMapping("users/address")
    public ResponseEntity<?> getUserByAddress(@RequestParam String address) {
        List<User> list = userDao.findUserByAddress(address);
        if(list == null || list.size() == 0) {
            LOGGER.error("User not found");
            ApiResponse<String> response = new ApiResponse<>(0, 404, "User not found", null);
            return ResponseEntity.ok(response);
        }
        ApiResponse<List<User>> response = new ApiResponse<>(1, 200, "User found", list);
        return ResponseEntity.ok(response);
    }
    @GetMapping("users/sort")
    public ResponseEntity<?> sortUsersByName() {
        List<User> users = userDao.sortUsersByName();
        if(users.size() == 0 || users == null) {
            LOGGER.error("No users found");
            ApiResponse<String> response = new ApiResponse<>(0, 404, "User not found", null);
            return ResponseEntity.ok(response);
        }
        ApiResponse<List<User>> response = new ApiResponse<>(1, 200, "Users sorted by name", users);
        return ResponseEntity.ok(response);
    }
    @PostMapping("users")
    public ResponseEntity<?> saveUser(@RequestBody User user) {
        String mess = userDao.validateUser(user);
        if(!mess.equals("true")){
            LOGGER.error(mess);
            ApiResponse<String> response = new ApiResponse<>(0, 400, mess, null);
            return ResponseEntity.ok(response);
        }
        String message = userDao.insertUser(user);
        ApiResponse<String> response = new ApiResponse<>(1, 200, message, null);
        return ResponseEntity.ok(response);
    }
    @PutMapping("users/{id}")
    public ResponseEntity<?> updateUser(@PathVariable int id, @RequestBody User user) {
        Boolean isUpdateSuccess = userDao.updateUser(user, id);
        if (!isUpdateSuccess) {
            LOGGER.error("User not found");
            ApiResponse<String> response = new ApiResponse<>(0, 404, "User not found", null);
            return ResponseEntity.ok(response);
        }
        ApiResponse<String> response = new ApiResponse<>(1, 200, "User updated successfully", null);
        return ResponseEntity.ok(response);
    }
    @DeleteMapping("users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable int id) {
        Boolean isDelSuccess = userDao.deleteUserById(id);
        if (!isDelSuccess) {
            LOGGER.error("User not found");
            ApiResponse<String> response = new ApiResponse<>(0, 404, "User not found", null);
            return ResponseEntity.ok(response);
        }
        ApiResponse<String> response = new ApiResponse<>(1, 200, "User deleted successfully", null);
        return ResponseEntity.ok(response);
    }
}
