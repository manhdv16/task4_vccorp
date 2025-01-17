package com.example.demotask4.dao.impl;

import com.example.demotask4.dao.UserDao;
import com.example.demotask4.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UserDaoImpl implements UserDao {
    private static final Logger LOGGER = LogManager.getLogger();
    private String jdbcURL= "jdbc:mysql://localhost:3306/jdbc_task";
    private String jdbcUsername= "root";
    private String jdbcPassword= "dvm123";

    private static final String INSERT_USER= "INSERT INTO user(name,email,phone,address,age) VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE_USER= "UPDATE user SET name = ?, email = ?, phone = ?, address = ?, age = ? WHERE id = ?";
    private static final String DELETE_USER_BY_ID ="DELETE FROM user WHERE id=?";
    private static final String SORT_USER_BY_NAME= "SELECT * FROM user ORDER BY name ASC";
    private static final String SELECT_USER_BY_ID= "select * from user where id=?";
    private static final String SELECT_USER_BY_NAME="select * from user where name=?";
    private static final String SELECT_USER_BY_ADDRESS="select * from user where address=?";

    protected Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection= DriverManager.getConnection(jdbcURL,jdbcUsername,jdbcPassword);
        } catch (Exception e) {
            LOGGER.error("Error in getting connection");
            e.printStackTrace();
        }
        return connection;
    }
    @Override
    public String insertUser(User user) {
        try(Connection connection = getConnection()) {
            LOGGER.info("Inserting user");
            PreparedStatement ps =  connection.prepareStatement(INSERT_USER);
            int result = 0;
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPhone());
            ps.setString(4, user.getAddress());
            ps.setInt(5, user.getAge());
            result = ps.executeUpdate();
            if(result > 0) {
                LOGGER.info("User inserted successfully");
                return "User inserted successfully";
            } else {
                LOGGER.error("Error in inserting user");
                return "Error in inserting user";
            }
        } catch (Exception e) {
            LOGGER.error("Error in inserting user", e);
            return "Error in inserting user";
        }
    }
    @Override
    public Boolean updateUser(User user, int id) {
        try(Connection connection = getConnection()) {
            LOGGER.info("Updating user");
            PreparedStatement ps =  connection.prepareStatement(UPDATE_USER);
            int result = 0;
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPhone());
            ps.setString(4, user.getAddress());
            ps.setInt(5, user.getAge());
            ps.setInt(6, id);
            result = ps.executeUpdate();
            if(result > 0) {
                LOGGER.info("User updated successfully");
                return true;
            } else {
                LOGGER.error("Error in updating user");
                return false;
            }
        } catch (Exception e) {
            LOGGER.error("Error in updating user", e);
            return false;
        }
    }
    @Override
    public Boolean deleteUserById(int id) {
        try(Connection connection = getConnection()) {
            LOGGER.info("Deleting user");
            PreparedStatement ps = connection.prepareStatement(DELETE_USER_BY_ID);
            int result = 0;
            ps.setInt(1, id);
            result = ps.executeUpdate();
            if(result > 0) {
                LOGGER.info("User deleted successfully");
                return true;
            } else {
                LOGGER.error("Error in deleting user");
                return false;
            }
        }catch (Exception e) {
            LOGGER.error("Error in deleting user", e);
            return false;
        }
    }
    @Override
    public User findUserById(int id) {
        try(Connection connection = getConnection()) {
          LOGGER.info("Finding user by id");
          PreparedStatement ps = connection.prepareStatement(SELECT_USER_BY_ID);
          ResultSet rs = null;
          ps.setInt(1, id);
          rs = ps.executeQuery();
          if(rs.next()) {
            User user = new User();
            user.setName(rs.getString("name"));
            user.setEmail(rs.getString("email"));
            user.setPhone(rs.getString("phone"));
            user.setAddress(rs.getString("address"));
            user.setAge(rs.getInt("age"));
            LOGGER.info("User found successfully");
            return user;
          } else {
            LOGGER.error("User not found");
            return null;
          }
        }catch (Exception e) {
            LOGGER.error("Error in finding user by id", e);
            return null;
        }
    }
    @Override
    public User findUserByName(String name) {
        try(Connection connection = getConnection()) {
          LOGGER.info("Finding user by name");
          PreparedStatement ps = connection.prepareStatement(SELECT_USER_BY_NAME);
          ResultSet rs = null;
          ps.setString(1, name);
          rs = ps.executeQuery();
          if(rs.next()) {
            User user = new User();
            user.setName(rs.getString("name"));
            user.setEmail(rs.getString("email"));
            user.setPhone(rs.getString("phone"));
            user.setAddress(rs.getString("address"));
            user.setAge(rs.getInt("age"));
            LOGGER.info("User found successfully");
            return user;
          } else {
            LOGGER.error("User not found");
            return null;
          }
        }catch (Exception e) {
            LOGGER.error("Error in finding user by name", e);
            return null;
        }
    }
    @Override
    public List<User> findUserByAddress(String address) {
        List<User> users = new ArrayList<>();
        try(Connection connection = getConnection()) {
            LOGGER.info("Finding user by address");
            PreparedStatement ps = connection.prepareStatement(SELECT_USER_BY_ADDRESS);
            ResultSet rs = null;
            ps.setString(1, address);
            rs = ps.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setPhone(rs.getString("phone"));
                user.setAddress(rs.getString("address"));
                user.setAge(rs.getInt("age"));
                users.add(user);
            }
            if (users.size() > 0) {
                LOGGER.info("User found successfully");
                return users;
            } else {
                LOGGER.error("User not found");
                return null;
            }
        }catch (Exception e) {
            LOGGER.error("Error in finding user by address", e);
            return null;
        }
    }

    @Override
    public List<User> sortUsersByName() {
        try(Connection connection = getConnection()) {
            LOGGER.info("Sorting users by name");
            PreparedStatement ps = connection.prepareStatement(SORT_USER_BY_NAME);
            ResultSet rs = null;
            rs = ps.executeQuery();
            List<User> users = new ArrayList<>();
            while(rs.next()) {
                User user = new User();
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setPhone(rs.getString("phone"));
                user.setAddress(rs.getString("address"));
                user.setAge(rs.getInt("age"));
                users.add(user);
            }
            return users;
        }catch (Exception e) {
            LOGGER.error("Error in sorting users by name", e);
            return null;
        }
    }

    @Override
    public String validateUser(User user) {
        if(user.getAge()>100 && user.getAge()<0){
            return "Age should be between 0 and 100";
        }
        if(user.getName().equals("")){
            return "Name should not be empty";
        }
        if(user.getAddress().equals("")){
            return "Address should not be empty";
        }
        return "true";
    }

    @Override
    public Boolean generateRandomUsers(int n) {
        try (Connection connection = getConnection()){
            PreparedStatement ps = connection.prepareStatement(INSERT_USER);
            int batchSize = 500000;
            int numberOfThreads = 10;
            ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
            List<User> users = new ArrayList<>(batchSize);
            User user=null;
            for (int i = 0; i < n; i++) {
                user = new User();
                user.setName("User" + i);
                user.setEmail("user" + i + "@example.com");
                users.add(user);
                if (users.size() == batchSize) {
                    List<User> batch = new ArrayList<>(users);
                    executorService.submit(() -> saveBatch(batch,ps));
                    users.clear();
                }
            }
            if (!users.isEmpty()) {
                saveBatch(users, ps);
            }
            executorService.shutdown();
            while (!executorService.isTerminated()) {
            }
            return true;
        }catch (Exception e) {
            LOGGER.error("Error in sorting users by name", e);
            return null;
        }
    }
    private void saveBatch(List<User> users,PreparedStatement ps) {
        try {
            for (User user : users) {
                ps.setString(1, user.getName());
                ps.setString(2, user.getEmail());
                ps.setString(3, user.getPhone());
                ps.setString(4, user.getAddress());
                ps.setInt(5, user.getAge());
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException e) {
            LOGGER.error("Error in saving batch", e);
        }
    }
}
