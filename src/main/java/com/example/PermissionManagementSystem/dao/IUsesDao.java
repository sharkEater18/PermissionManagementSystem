package com.example.PermissionManagementSystem.dao;

import com.example.PermissionManagementSystem.enums.Mode;
import com.example.PermissionManagementSystem.pojos.Document;
import com.example.PermissionManagementSystem.pojos.User;

public interface IUsesDao {
    public void insertUses(User user, Document document, Mode mode);
    public void updateMode(Document document, User user, Mode mode);
    public void deleteFromUses(User user, Document document);
}
