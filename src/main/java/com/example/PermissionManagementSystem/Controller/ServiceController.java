package com.example.PermissionManagementSystem.Controller;

import com.example.PermissionManagementSystem.dao.UserDao;
import com.example.PermissionManagementSystem.enums.Mode;
import com.example.PermissionManagementSystem.pojos.Document;
import com.example.PermissionManagementSystem.pojos.User;
import com.example.PermissionManagementSystem.services.DocumentService1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ServiceController {
    @Autowired
    private DocumentService1 documentService1;

    @Autowired
    private UserDao userDao;

//    @RequestMapping("/documents/{docId}")
//    public Document getDocument(@PathVariable String docId) {
//        return DocumentService1.documentDao.getDocument(docId);
//    }

//    @RequestMapping("/documents")
//    public List<Document> getAllDocuments() {
//        return DocumentService1.documentDao.getAllDocuments();
//    }

    @RequestMapping(method = RequestMethod.POST, value = "/documents")
    public void addDocument(@RequestBody Document document) {
        DocumentService1.documentDao.insertDocument(document);
    }

    @RequestMapping("/users/{userId}")
    public User getUser(@PathVariable String userId) {
        return userDao.getUser(userId);
    }

    @RequestMapping("/users")
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/users")
    public void addUser(@RequestBody User user) {
        userDao.insertUser(user);
    }


    @RequestMapping(method = RequestMethod.POST, value = "/documents/{userId}")
    public void createDocument(@PathVariable String userId, @RequestBody Document document) {
        documentService1.create(userId, document);
    }

    @RequestMapping("/documents/get")
    public Document getDocument(@RequestParam String userId, @RequestParam String docId) {
        return documentService1.getDocument(userId, docId);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/documents/put")
    public void updateDocumentContent(@RequestParam String userId, @RequestParam String docId, @RequestParam(required = false) String newContent) {
        documentService1.update(userId, docId, newContent);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/documents/delete")
    public void deleteDocument(@RequestParam String userId, @RequestParam String docId) {
        documentService1.delete(userId, docId);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/documents/grant")
    public void grantDocument(@RequestParam String superUserId, @RequestParam String userId, @RequestParam String docId, @RequestParam Mode mode) {
        System.out.println(mode);
        documentService1.grant(superUserId, userId, docId, mode);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/documents/revoke")
    public void revoke(@RequestParam String superUserId, @RequestParam String userId, @RequestParam String docId, @RequestParam Mode mode) {
        documentService1.revoke(superUserId, userId, docId, mode);
    }
}
