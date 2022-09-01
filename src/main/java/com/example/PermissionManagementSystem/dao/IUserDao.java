package com.example.PermissionManagementSystem.dao;

import com.example.PermissionManagementSystem.pojos.User;

import java.util.List;

public interface IUserDao {
    public void insertUser(String userId);
    public void insertUser(User user);
    public List<User> getAllUsers();
    public User getUser(String userid);
    public void updateUser(String userId);
    public void updateUser(User user);
    public void deleteUser(String userId);
    public void deleteUser(User user);
}
