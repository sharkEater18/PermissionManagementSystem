DROP DATABASE IF EXISTS `sql_permission_management_system`;
CREATE DATABASE `sql_permission_management_system`; 
USE `sql_permission_management_system`;

SET NAMES utf8 ;
SET character_set_client = utf8mb4 ;


-- Tables

CREATE TABLE `users` (
  `user_id` varchar(50) NOT NULL,
  PRIMARY KEY (`user_id`)
);

CREATE TABLE `documents` (
  `doc_id` varchar(50) NOT NULL,
  `doc_owner_id` varchar(50)NOT NULL,
  `doc_name` varchar(50) NOT NULL,
  `doc_content` text NOT NULL,
  PRIMARY KEY (`doc_id`)
);

-- ALTER TABLE documents RENAME COLUMN doc_owner_id TO doc_owner_id ;

CREATE TABLE `uses` (
  `user_id` varchar(50) NOT NULL,
  `doc_id` varchar(50) NOT NULL,
  `access_type` ENUM('READ', 'WRITE', 'NONE') NOT NULL,
  PRIMARY KEY (`user_id`, `doc_id`),
  FOREIGN KEY (user_id) REFERENCES users(user_id), 
  FOREIGN KEY (doc_id) REFERENCES documents(doc_id) 
);


-- Indexes
CREATE INDEX idx_userId ON users (user_id); 

CREATE INDEX idx_userId ON documents (doc_id); 

-- To find the ordering for composite indexing
-- SELECT 
-- 	COUNT(DISTINCT user_id),
--     COUNT(DISTINCT doc_id)
-- FROM uses;

CREATE INDEX idx_userId_docId ON uses (user_id, doc_id); 


-- Stored Procedures
DELIMITER $$ 
CREATE PROCEDURE `sql_permission_management_system`.insert_into_users(userId varchar(50))
BEGIN
	SET foreign_key_checks = 0; 
	INSERT INTO `users` VALUES(userId);
    SET foreign_key_checks = 1; 
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE `sql_permission_management_system`.insert_into_documents(
	docId varchar(50),
	parentId varchar(50),
    nameOfDoc varchar(50),
    content text
)
BEGIN
	IF content IS NULL THEN
		SET content = '';
	END IF;
	INSERT INTO `documents` VALUES(docId, parentId, nameOfDoc, content);
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE `sql_permission_management_system`.insert_into_uses(
    userId varchar(50),
	docId varchar(50),
    accessType ENUM('READ', 'WRITE', 'NONE')
)
BEGIN
	INSERT INTO `uses` VALUES(userId, docId, accessType);
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE `sql_permission_management_system`.update_document_name(
	docId varchar(50),
    newName varchar(50)
)
BEGIN
	UPDATE `sql_permission_management_system`.documents 
    SET `sql_permission_management_system`.documents.doc_name = newName
    WHERE `sql_permission_management_system`.documents.doc_id = docId;
END $$
DELIMITER ;


DELIMITER $$
CREATE PROCEDURE `sql_permission_management_system`.update_document_content(
	docId varchar(50),
    newContent varchar(50)
)
BEGIN
	UPDATE `sql_permission_management_system`.documents 
    SET `sql_permission_management_system`.documents.doc_content = newContent
    WHERE `sql_permission_management_system`.documents.doc_id = docId;
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE `sql_permission_management_system`.update_document_name_with_content(
	docId varchar(50),
    newName varchar(50),
    newContent varchar(50)
)
BEGIN
	UPDATE `sql_permission_management_system`.documents 
    SET `sql_permission_management_system`.documents.doc_name = newName, `sql_permission_management_system`.documents.doc_content = newContent
    WHERE `sql_permission_management_system`.documents.doc_id = docId;
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE `sql_permission_management_system`.delete_user(
	userId varchar(50)
)
BEGIN
	SET foreign_key_checks = 0; 
	DELETE FROM `sql_permission_management_system`.users 
    WHERE `sql_permission_management_system`.users.user_id = userId;
    SET foreign_key_checks = 1; 
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE `sql_permission_management_system`.delete_document(
	docId varchar(50)
)
BEGIN
	SET foreign_key_checks = 0; 
	DELETE FROM `sql_permission_management_system`.documents 
    WHERE `sql_permission_management_system`.documents.doc_id = docId;
    SET foreign_key_checks = 1; 
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE `sql_permission_management_system`.delete_on_uses_by_doc_id(
	docId varchar(50)
)
BEGIN
	DELETE FROM `sql_permission_management_system`.uses 
    WHERE `sql_permission_management_system`.uses.doc_id = docId;
END $$
DELIMITER ;


DELIMITER $$
CREATE PROCEDURE `sql_permission_management_system`.delete_on_uses_by_user_id(
	userId varchar(50)
)
BEGIN
	DELETE FROM `sql_permission_management_system`.uses 
    WHERE `sql_permission_management_system`.uses.user_id = userId;
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE `sql_permission_management_system`.delete_on_uses(
	userId varchar(50),
    docId varchar(50)
)
BEGIN
	DELETE FROM `sql_permission_management_system`.uses 
    WHERE `sql_permission_management_system`.uses.user_id = userId AND `sql_permission_management_system`.uses.doc_id = docId;
END $$
DELIMITER ;

-- DELIMITER $$
-- CREATE PROCEDURE `sql_permission_management_system`.update_mode(
-- 	mode 
-- )
-- BEGIN
-- 	UPDATE `sql_permission_management_system`.uses 
--     SET  `sql_permission_management_system`.uses.access_type = mode
--     WHERE `sql_permission_management_system`.uses.user_id = userId;
-- END $$
-- DELIMITER ;

DELIMITER $$
CREATE PROCEDURE `sql_permission_management_system`.delete_on_documents_by_user_id(
	userId varchar(50)
)
BEGIN
	DELETE FROM `sql_permission_management_system`.documents 
    WHERE `sql_permission_management_system`.documents.doc_owner_id = userId;
END $$
DELIMITER ;

-- TRIGGER
DELIMITER $$
CREATE TRIGGER `sql_permission_management_system`.insert_on_uses_after_insert_on_documents
	AFTER INSERT ON `sql_permission_management_system`.documents
    FOR EACH ROW
BEGIN 
	CALL `sql_permission_management_system`.insert_into_uses(NEW.doc_owner_id, NEW.doc_id, 'WRITE');
END $$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER `sql_permission_management_system`.delete_on_uses_after_delete_on_documents
	AFTER DELETE ON `sql_permission_management_system`.documents
    FOR EACH ROW
BEGIN 
	CALL `sql_permission_management_system`.delete_on_uses_by_doc_id(OLD.doc_id);
END $$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER `sql_permission_management_system`.delete_on_uses_after_delete_on_users
	AFTER DELETE ON `sql_permission_management_system`.users
    FOR EACH ROW
BEGIN 
	CALL `sql_permission_management_system`.delete_on_uses_by_user_id(OLD.user_id);
END $$
DELIMITER ;


DELIMITER $$
CREATE TRIGGER `sql_permission_management_system`.delete_on_documents_after_delete_on_users
	AFTER DELETE ON `sql_permission_management_system`.users
    FOR EACH ROW
BEGIN 
	CALL `sql_permission_management_system`.delete_on_documents_by_user_id(OLD.user_id);
END $$
DELIMITER ;




call sql_permission_management_system.insert_into_users(1);
call sql_permission_management_system.insert_into_users(2);
call sql_permission_management_system.insert_into_users(3);


call sql_permission_management_system.insert_into_documents(1, 1, 'doc1', 'hello');
call sql_permission_management_system.insert_into_documents(2, 1, 'doc2', 'bye');
call sql_permission_management_system.insert_into_documents(3, 2, 'doc3', 'bye_again');

-- call update_document_name(3, 'doc3_new');


-- call delete_user(1);


-- call delete_document(1);


SELECT * FROM users;
SELECT * FROM uses;
SELECT * FROM documents;


-- CALL insert_into_documents("4", "1", "doc4", "hello world!");