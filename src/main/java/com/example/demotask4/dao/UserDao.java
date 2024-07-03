package com.example.demotask4.dao;

import com.example.demotask4.model.User;

import java.util.List;

public interface UserDao {
    public String insertUser(User user);
    public Boolean updateUser(User user, int id);
    public Boolean deleteUserById(int id);
    public User findUserById(int id);
    public User findUserByName(String name);
    public List<User> findUserByAddress(String address);
    public List<User> sortUsersByName();
    public String validateUser(User user);
}
