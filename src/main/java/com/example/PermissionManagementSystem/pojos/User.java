package com.example.PermissionManagementSystem.pojos;

public class User {
    private String userId;

    public User() {}

    public User(String id) {
        this.userId = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
