package com.example.PermissionManagementSystem.pojos;

public class Document {
    private String docId;
    private String docOwnerId;
    private String docName;
    private String docContent;

    public Document(String docId, String docOwnerId, String docName, String docContent) {
        this.docId = docId;
        this.docOwnerId = docOwnerId;
        this.docName = docName;
        this.docContent = docContent;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getDocOwnerId() {
        return docOwnerId;
    }

    public void setDocOwnerId(String docOwnerId) {
        this.docOwnerId = docOwnerId;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public String getDocContent() {
        return docContent;
    }

    public void setDocContent(String docContent) {
        this.docContent = docContent;
    }
}
