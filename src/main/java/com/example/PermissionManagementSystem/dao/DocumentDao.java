package com.example.PermissionManagementSystem.dao;

import com.example.PermissionManagementSystem.pojos.Document;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Service
public class DocumentDao implements IDocumentDao {
    @Override
    public void insertDocument(String docId, String docOwnerId, String nameOfDoc, String docContent) {
        try {
            String query = "CALL insert_into_documents('" + docId + "', '" + docOwnerId + "', '" + nameOfDoc + "', " + "'" + docContent + "')";
//            System.out.println(query);
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/sql_permission_management_system", "root", "password");
            Statement st = con.createStatement();
            st.execute(query);
//            ResultSet rs = st.executeQuery(query);
//            rs.next();
            st.close();
            con.close();
        } catch (Exception e) {
            e.notifyAll();
            System.out.println("This document already exists!");
            e.printStackTrace();
        }
    }

    @Override
    public void insertDocument(Document document) {
        String docId = document.getDocId();
        String docOwnerId = document.getDocOwnerId();
        String nameOfDoc = document.getDocName();
        String docContent = document.getDocContent();
//        System.out.println(docId + " " + docOwnerId + " " + nameOfDoc + " " + docContent);
        insertDocument(docId, docOwnerId, nameOfDoc, docContent);
    }

    @Override
    public List<Document> getAllDocuments() {
        try {
            String query = "SELECT DISTINCT * FROM documents";
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/sql_permission_management_system", "root", "password");
            Statement st = con.createStatement();
//            st.execute(query);
            ResultSet rs = st.executeQuery(query);
            List<Document> documents = new ArrayList<>();
            while (rs.next()) {
                String docId = rs.getString("doc_id");
                String docOwnerId = rs.getString("doc_owner_id");
                String docName = rs.getString("doc_name");
                String docContent = rs.getString("doc_content");
                Document document = new Document(docId, docOwnerId, docName, docContent);
                documents.add(document);
            }
//            rs.next();
            st.close();
            con.close();
            return documents;
        } catch (Exception e) {
            e.notifyAll();
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Document getDocument(String docId) {
        try {
            String query = "SELECT * FROM documents WHERE doc_id=" + docId;
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/sql_permission_management_system", "root", "password");
            Statement st = con.createStatement();
//            st.execute(query);
            ResultSet rs = st.executeQuery(query);
            rs.next();
            String docOwnerId = rs.getString("doc_owner_id");
            String docName = rs.getString("doc_name");
            String docContent = rs.getString("doc_content");
            Document document = new Document(docId, docOwnerId, docName, docContent);
            st.close();
            con.close();
            return document;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void updateDocumentName(String docId, String newName) {
        try {
            String query = "CALL update_document_name(" + docId + ", '" + newName + "')";
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/sql_permission_management_system", "root", "password");
            Statement st = con.createStatement();
            st.execute(query);
            st.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateDocumentName(Document document, String newName) {
        String docId = document.getDocId();
        updateDocumentName(docId, newName);
    }

    @Override
    public void updateDocumentContent(String docId, String newContent) {
        try {
            String query = "CALL update_document_content(" + docId + ", '" + newContent + "')";
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/sql_permission_management_system", "root", "password");
            Statement st = con.createStatement();
            st.execute(query);
            st.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateDocumentContent(Document document, String newContent) {
        String docId = document.getDocId();
        updateDocumentContent(docId, newContent);
    }

    @Override
    public void updateDocumentNameContent(String docId, String newName, String newContent) {
        try {
            String query = "CALL update_document_name_with_content(" + docId + ", " + newName + ", '" + newContent + "')";
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/sql_permission_management_system", "root", "password");
            Statement st = con.createStatement();
            st.execute(query);
            st.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateDocumentNameContent(Document document, String newName, String newContent) {
        String docId = document.getDocId();
        updateDocumentNameContent(docId, newName, newContent);
    }

    @Override
    public void deleteDocument(String docId) {
        try {
            String query = "CALL delete_document('" + docId  + "')";
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/sql_permission_management_system", "root", "password");
            Statement st = con.createStatement();
            st.execute(query);
            st.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteDocument(Document document) {
        String docId = document.getDocId();
        deleteDocument(docId);
    }
}
