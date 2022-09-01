package com.example.PermissionManagementSystem.dao;

import com.example.PermissionManagementSystem.pojos.User;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserDao implements IUserDao {
    @Override
    public void insertUser(String userId) {
        try {
            String query = "CALL insert_into_users(" + userId + ")";
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
    public void insertUser(User user) {
        String userId = user.getUserId();
        insertUser(userId);
    }

    @Override
    public List<User> getAllUsers() {
        try {
            String query = "SELECT * FROM users";
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/sql_permission_management_system", "root", "password");
            Statement st = con.createStatement();
            List<User> users = new ArrayList<>();
//            st.execute(query);
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                String id = rs.getString("user_id");
                User user = new User(id);
                users.add(user);
            }
            st.close();
            con.close();
            return users;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public User getUser(String userId) {
        try {
            User user = new User(userId);
            String query = "SELECT user_id FROM users WHERE user_id=" + userId;
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/sql_permission_management_system", "root", "password");
            Statement st = con.createStatement();
//            st.execute(query);
//            ResultSet rs = st.executeQuery(query);
//            rs.next();
            st.close();
            con.close();
            return user;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void updateUser(String userId) {
//        try {
//            String userId = user.getUserId();
//            String query = "";
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/sql_permission_management_system", "root", "password");
//            Statement st = con.createStatement();
//            st.execute(query);
////            ResultSet rs = st.executeQuery(query);
////            rs.next();
//            st.close();
//            con.close();
//        } catch (Exception e) {
//            System.out.println(e);
//        }
    }

    @Override
    public void updateUser(User user) {
        String userId = user.getUserId();
        updateUser(userId);
    }

    @Override
    public void deleteUser(String userId) {
        try {
            String query = "CALL delete_user(" + userId + ")";
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
    public void deleteUser(User user) {
       String userId = user.getUserId();
       deleteUser(userId);
    }
}
