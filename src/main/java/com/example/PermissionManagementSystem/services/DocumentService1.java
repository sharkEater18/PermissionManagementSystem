package com.example.PermissionManagementSystem.services;

import com.example.PermissionManagementSystem.dao.DocumentDao;
import com.example.PermissionManagementSystem.dao.UsesDao;
import com.example.PermissionManagementSystem.enums.Mode;
import com.example.PermissionManagementSystem.pojos.Document;
import com.example.PermissionManagementSystem.pojos.User;
import com.example.PermissionManagementSystem.services.IDocumentService;
import org.springframework.stereotype.Service;

import java.sql.*;

@Service
public class DocumentService1 implements IDocumentService {

    public static DocumentDao documentDao = new DocumentDao();

    public static UsesDao usesDao = new UsesDao();

    @Override
    public Document create(User user, String name, String docId, String content) {
        try {
            String docOwnerId = user.getUserId();
            documentDao.insertDocument(docId, docOwnerId, name, content);
            return new Document(docId, docOwnerId, name, content);
        } catch (Exception e) {
            System.out.println("Can't Create!");
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public Document create(User user, String name, String docId) {
        return create(user, name, docId, "");
    }

    private Mode modeMode(String strMode) {
        if (strMode.startsWith("READ")) return Mode.READ;
        else if (strMode.startsWith("WRITE")) return Mode.WRITE;
        else return Mode.NONE;
    }

    public Document create(String userId, Document document) {
        try {
            documentDao.insertDocument(document.getDocId(), userId, document.getDocName(), document.getDocContent());
            return document;
        } catch (Exception e) {
            System.out.println("Can't Create!");
            e.printStackTrace();
            return null;
        }
    }

    private Mode whatMode(String userId, String docId) {
        try {
            String query = "SELECT * FROM uses WHERE user_id='" + userId + "'AND doc_id='" + docId + "'";
//            System.out.println(query);
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/sql_permission_management_system", "root", "password");
            Statement st = con.createStatement();
//            st.execute(query);
            ResultSet rs = st.executeQuery(query);
            rs.next();
            String strMode = rs.getString("access_type");
            st.close();
            con.close();
            return modeMode(strMode);
        } catch (Exception e) {
            e.printStackTrace();;
            return Mode.NONE;
        }
    }

    // TODO make it short
    private Mode whatMode(User user, Document document) {
        String userId = user.getUserId();
        String docId = document.getDocId();
        String docOwnerId = document.getDocOwnerId();

        if (userId == docOwnerId) {
            return Mode.WRITE;
        }

        try {
            String query = "SELECT * FROM uses WHERE user_id='" + userId + "'AND doc_id='" + docId + "'";
//            System.out.println(query);
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/sql_permission_management_system", "root", "password");
            Statement st = con.createStatement();
//            st.execute(query);
            ResultSet rs = st.executeQuery(query);
            rs.next();
            String strMode = rs.getString("access_type");
//            System.out.println("");
//            System.out.println(strMode);
//            System.out.println("");
            st.close();
            con.close();
            return modeMode(strMode);
        } catch (Exception e) {
            e.printStackTrace();;
            return Mode.NONE;
        }
    }

    @Override
    public void read(User user, Document document) {
        String userId = user.getUserId();
        String docId = document.getDocId();
        String docOwnerId = document.getDocOwnerId();

        Mode mode = whatMode(user, document);
//        System.out.println("mode in read" + mode);

        if (mode == Mode.NONE) {
            System.out.println(userId + ", Contact " + docOwnerId + " for read access!");
        } else {
//            System.out.println(document.getDocContent());
            try {
                String query = "SELECT * FROM documents WHERE doc_id=" + docId ;
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/sql_permission_management_system", "root", "password");
                Statement st = con.createStatement();
//            st.execute(query);
                ResultSet rs = st.executeQuery(query);
                rs.next();
                String docContent = rs.getString("doc_content");
                System.out.println(docContent);
                st.close();
                con.close();
            } catch (Exception e) {
                e.notifyAll();
                e.printStackTrace();
            }
        }
    }

    public String getDocOwnerId(String docId) {
        return documentDao.getDocument(docId).getDocOwnerId();
    }

    public Document getDocument(String userId, String docId) {
        Mode mode = whatMode(userId, docId);
        if (mode != Mode.NONE) {
            return documentDao.getDocument(docId);
        } else {
            System.out.println("Hey " + userId + ", You don't have READ privileges! contact " + getDocOwnerId(docId));
            return null;
        }
    }



    // TODO incl in the interface and make the next method shorter
    public void update(String userId, String docId, String newText) {
        Mode mode = whatMode(userId, docId);

        if (mode != Mode.WRITE) {
            System.out.println("Don't have privileges!");
            // TODO make an method that fetches owner info from the DB
//            System.out.println(userId + ", Contact " + docOwnerId + " for write access!");
        } else {
            documentDao.updateDocumentContent(docId, newText);
        }
    }

    @Override
    public void update(User user, Document document, String newText) {
        String userId = user.getUserId();
        String docId = document.getDocId();
        String docOwnerId = document.getDocOwnerId();

        if (userId == docOwnerId) {
            documentDao.updateDocumentContent(docId, newText);
            return;
        }

        Mode mode = whatMode(user, document);

        if (mode != Mode.WRITE) {
            System.out.println(userId + ", Contact " + docOwnerId + " for write access!");
        } else {
            documentDao.updateDocumentContent(docId, newText);
        }
    }

    @Override
    public void delete(User user, Document document) {
        String userId = user.getUserId();
        String docId = document.getDocId();
        String docOwnerId = document.getDocOwnerId();

        if (userId == docOwnerId) {
            documentDao.deleteDocument(document);
        } else {
            System.out.println("You aren't authorized for delete action!");
        }
    }

    public void delete(String userId, String docId) {
//        System.out.println(userId + " " + getDocOwnerId(docId));
        if (userId.startsWith(getDocOwnerId(docId))) {
            documentDao.deleteDocument(docId);
        } else {
            System.out.println("You aren't authorized for delete action!");
        }
    }

    @Override
    public void grant(User superUser, User user, Document document, Mode mode) {
        String userId = user.getUserId();
        String docId = document.getDocId();
        String docOwnerId = document.getDocOwnerId();

        if (mode == Mode.NONE) {
            System.out.println("Invalid Request!");
            return;
        }

        if (superUser.getUserId() == docOwnerId) {
            if (userId == docOwnerId) {
                System.out.println("You already have write access for " + docId);
                return;
            }

            Mode newMode = Mode.NONE;
            try {
                Mode givenMode = whatMode(user, document);
//                System.out.println(givenMode);
                if (givenMode == null) {
                    throw new Exception();
                }
                if (mode == Mode.READ) {
                    if (givenMode == Mode.NONE) {
                        newMode = Mode.READ;
                        usesDao.insertUses(user, document, Mode.READ);
                    } else {
                        System.out.println("You already have permission to read the document " + document.getDocName());
                    }
                } else {
                    if (givenMode == Mode.NONE) {
                        usesDao.insertUses(user, document, Mode.WRITE);
                    } if (givenMode == Mode.WRITE) {
                        System.out.println("You already have permission to make changes in the document " + document.getDocName());
                    } else {
                        newMode = Mode.WRITE;
                        usesDao.updateMode(document, user, newMode);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
//                System.out.println("Created an entry!!");
//                newMode = mode;
//                usesDao.insertUses(user, document, newMode);
            }
        } else {
            System.out.println("You aren't authorized for grant privilege, contact " + docOwnerId + " for this action!");
        }
    }

    public void grant(String superUserId, String userId, String docId, Mode mode) {
        System.out.println(mode);
        String docOwnerId = getDocOwnerId(docId);

        if (mode == Mode.NONE) {
            System.out.println("Invalid Request!");
            return;
        }

        if (superUserId.startsWith(docOwnerId)) {
            if (userId == docOwnerId) {
                System.out.println("You already have write access for " + docId);
                return;
            }

            Mode newMode = Mode.NONE;
            try {
                Mode givenMode = whatMode(userId, docId);
//                System.out.println(givenMode);
                if (givenMode == null) {
                    throw new Exception();
                }
                if (mode == Mode.READ) {
                    if (givenMode == Mode.NONE) {
                        newMode = Mode.READ;
                        usesDao.insertUses(userId, docId, Mode.READ);
                    } else {
                        System.out.println("You already have permission to read the document " + docId);
                    }
                } else {
                    if (givenMode == Mode.NONE) {
                        usesDao.insertUses(userId, docId, Mode.WRITE);
                    } if (givenMode == Mode.WRITE) {
                        System.out.println("You already have permission to make changes in the document " + docId);
                    } else {
                        newMode = Mode.WRITE;
                        usesDao.updateMode(docId, userId, newMode);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
//                System.out.println("Created an entry!!");
//                newMode = mode;
//                usesDao.insertUses(user, document, newMode);
            }
        } else {
            System.out.println("You aren't authorized for grant privilege, contact " + docOwnerId + " for this action!");
        }
    }

    @Override
    public void revoke(User superUser, User user, Document document, Mode mode) {
        String userId = user.getUserId();
        String docId = document.getDocId();
        String docOwnerId = document.getDocOwnerId();

        if (mode == Mode.NONE) {
            System.out.println("Invalid Request!");
            return;
        }

        if (superUser.getUserId().startsWith(docOwnerId)) {
            if (userId == docOwnerId) {
                System.out.println("You can't revoke yourself for " + docId);
                return;
            }

            try {
                Mode givenMode = whatMode(user, document);

                if (givenMode == null) {
                    throw new Exception();
                }

                if (givenMode == Mode.NONE) {
                    assert false;
                    System.out.println("Can't revoke further!");
                    return;
                }

                if (mode == Mode.READ) {
                    usesDao.deleteFromUses(user, document);
                } else {
                    if (givenMode == Mode.WRITE) {
                        usesDao.updateMode(document, user, Mode.READ);
                    } else {
                        System.out.println("Can't revoke further!");
                    }
                }
            } catch (Exception e) {
                System.out.println("Nothing to revoke!");
            }
        } else {
            System.out.println("You aren't authorized for revoke privilege, contact " + docOwnerId + " for this action!");
        }
    }

    public void revoke(String superUserId, String userId, String docId, Mode mode) {
        String docOwnerId = getDocOwnerId(docId);

        if (mode == Mode.NONE) {
            System.out.println("Invalid Request!");
            return;
        }

        if (superUserId.startsWith(docOwnerId)) {
            if (userId == docOwnerId) {
                System.out.println("You can't revoke yourself for " + docId);
                return;
            }

            try {
                Mode givenMode = whatMode(userId, docId);

                if (givenMode == null) {
                    throw new Exception();
                }

                if (givenMode == Mode.NONE) {
                    System.out.println("Can't revoke further!");
                    assert false;
                    return;
                }

                if (mode == Mode.READ) {
                    usesDao.deleteFromUses(userId, docId);
                } else {
                    if (givenMode == Mode.WRITE) {
                        usesDao.updateMode(docId, userId, Mode.READ);
                    } else {
                        System.out.println("Can't revoke further!");
                    }
                }
            } catch (Exception e) {
                System.out.println("Nothing to revoke!");
            }
        } else {
            System.out.println("You aren't authorized for revoke privilege, contact " + docOwnerId + " for this action!");
        }
    }
}
