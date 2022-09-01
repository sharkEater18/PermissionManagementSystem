package com.example.PermissionManagementSystem.dao;

import com.example.PermissionManagementSystem.pojos.Document;

import java.util.List;

public interface IDocumentDao {
    public List<Document> getAllDocuments();
    public void insertDocument(String docId, String docOwnerId, String docName, String docContent);
    public void insertDocument(Document document);
    public Document getDocument(String docId);
    public void updateDocumentName(String docId, String newName);
    public void updateDocumentName(Document document, String newName);
    public void updateDocumentContent(String docId, String newContent);
    public void updateDocumentContent(Document document, String newContent);
    public void updateDocumentNameContent(String docId, String newName, String newContent);
    public void updateDocumentNameContent(Document document, String newName, String newContent);
    public void deleteDocument(String docId);
    public void deleteDocument(Document document);
}
