package com.example.demotask4.dao;

import com.example.demotask4.model.User;

import java.util.List;

public interface UserDao {
    public String insertUser(User user);
    public void updateUser(User user, int id);
    public void deleteUserById(int id);
    public User findUserById(int id);
    public User findUserByName(String name);
    public List<User> findUserByAddress(String address);
    public void sortUsersByName();
    public String validateUser(User user);
}
