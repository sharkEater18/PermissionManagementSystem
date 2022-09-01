package com.example.PermissionManagementSystem.dao;

import com.example.PermissionManagementSystem.enums.Mode;
import com.example.PermissionManagementSystem.pojos.Document;
import com.example.PermissionManagementSystem.pojos.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class UsesDao implements IUsesDao {
    @Override
    public void insertUses(User user, Document document, Mode mode) {
        String docId = document.getDocId();
        String userId = user.getUserId();
        try {
            String query = "CALL insert_into_uses('" + userId + "','" + docId + "','" + mode + "')";
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
            e.printStackTrace();
        }
    }

    public void insertUses(String userId, String docId, Mode mode) {
        try {
            String query = "CALL insert_into_uses('" + userId + "','" + docId + "','" + mode + "')";
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
            e.printStackTrace();
        }
    }

    public void updateMode(String docId, String userId, Mode mode) {
        try {
            String query = "UPDATE uses SET access_type='" + mode + "' WHERE user_id=" + userId + " AND doc_id=" + docId;
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
            e.printStackTrace();
        }
    }

    @Override
    public void updateMode(Document document, User user, Mode mode) {
        String docId = document.getDocId();
        String userId = user.getUserId();
        try {
            String query = "UPDATE uses SET access_type='" + mode + "' WHERE user_id=" + userId + " AND doc_id=" + docId;
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
            e.printStackTrace();
        }
    }

    @Override
    public void deleteFromUses(User user, Document document) {
        String docId = document.getDocId();
        String userId = user.getUserId();
        try {
            String query = "CALL delete_on_uses('" + userId + "','" + docId + "')";
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
            e.printStackTrace();
        }
    }

    public void deleteFromUses(String userId, String docId) {
        try {
            String query = "CALL delete_on_uses('" + userId + "','" + docId + "')";
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
            e.printStackTrace();
        }
    }
}
