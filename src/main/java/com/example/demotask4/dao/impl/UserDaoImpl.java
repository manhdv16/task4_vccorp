package com.example.demotask4.dao.impl;

import com.example.demotask4.dao.UserDao;
import com.example.demotask4.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

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
            }
        } catch (Exception e) {
            LOGGER.error("Error in inserting user");
            e.printStackTrace();
        }finally {
            return "Error in inserting user";
        }
    }
    @Override
    public void updateUser(User user, int id) {
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
            } else {
                LOGGER.error("Error in updating user");
            }
        } catch (Exception e) {
            LOGGER.error("Error in updating user");
            e.printStackTrace();
        }
    }
    @Override
    public void deleteUserById(int id) {
        try(Connection connection = getConnection()) {
            LOGGER.info("Deleting user");
            PreparedStatement ps = connection.prepareStatement(DELETE_USER_BY_ID);
            int result = 0;
            ps.setInt(1, id);
            result = ps.executeUpdate();
            if(result > 0) {
                LOGGER.info("User deleted successfully");
            } else {
                LOGGER.error("Error in deleting user");
            }
        }catch (Exception e) {
            LOGGER.error("Error in deleting user");
            e.printStackTrace();
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
          }
        }catch (Exception e) {
            LOGGER.error("Error in finding user by id");
            e.printStackTrace();
        }
        finally {
            LOGGER.error("User not found");
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
          }
        }catch (Exception e) {
            LOGGER.error("Error in finding user by name");
            e.printStackTrace();
        }finally {
            LOGGER.error("User not found");
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
            }
        }catch (Exception e) {
            LOGGER.error("Error in finding user by address");
            e.printStackTrace();
        }finally {
            LOGGER.error("User not found");
            return null;
        }
    }

    @Override
    public void sortUsersByName() {
        try(Connection connection = getConnection()) {
            LOGGER.info("Sorting users by name");
            PreparedStatement ps = connection.prepareStatement(SORT_USER_BY_NAME);
            ResultSet rs = null;
            rs = ps.executeQuery();
            while(rs.next()) {
                User user = new User();
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setPhone(rs.getString("phone"));
                user.setAddress(rs.getString("address"));
                user.setAge(rs.getInt("age"));
                LOGGER.info(user);
            }
        }catch (Exception e) {
            LOGGER.error("Error in sorting users by name");
            e.printStackTrace();
        } finally {
            LOGGER.error("Error in sorting users by name");
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
}
