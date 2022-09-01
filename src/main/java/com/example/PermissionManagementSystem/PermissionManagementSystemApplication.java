package com.example.PermissionManagementSystem;

import com.example.PermissionManagementSystem.dao.UserDao;
import com.example.PermissionManagementSystem.enums.Mode;
import com.example.PermissionManagementSystem.pojos.Document;
import com.example.PermissionManagementSystem.pojos.User;
import com.example.PermissionManagementSystem.services.DocumentService1;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PermissionManagementSystemApplication {

	public static void main(String[] args) {

		SpringApplication.run(PermissionManagementSystemApplication.class, args);

//		DocumentService1 documentService1 = new DocumentService1();
//		UserDao userDao = new UserDao();
//		User user1 = new User("1");
////		userDao.insertUser(user1);
//		Document doc4 = documentService1.create(user1, "doc4", "4", "HelloWorld4");
////		documentService1.read(user1, doc4);
////		documentService1.update(user1, doc4, "new Hello world!!");
////		documentService1.delete(user1, doc4);
//
//		User user7 = new User("7");
//		userDao.insertUser(user7);
//		documentService1.grant(user1, user7, doc4, Mode.WRITE);
//
//
////		documentService1.update(user7, doc4, "updated!!!");
////		documentService1.read(user7, doc4);
//
//
//
//		documentService1.revoke(user1, user7, doc4, Mode.WRITE);
//		documentService1.update(user1, doc4, "updated!!!__:)");
	}

}
