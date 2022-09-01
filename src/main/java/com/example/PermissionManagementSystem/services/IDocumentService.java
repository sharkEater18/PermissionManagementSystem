package com.example.PermissionManagementSystem.services;

import com.example.PermissionManagementSystem.enums.Mode;
import com.example.PermissionManagementSystem.pojos.Document;
import com.example.PermissionManagementSystem.pojos.User;

public interface IDocumentService {
    Document create(User user, String name, String docId);
    Document create(User user, String name, String docId, String content);
    void read(User user, Document document);
    void update(User user, Document document, String newText);
    void delete(User user, Document document);
    void grant(User superUser, User user, Document document, Mode mode);
    void revoke(User superUser, User user, Document document, Mode mode);
}
