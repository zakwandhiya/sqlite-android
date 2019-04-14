package com.papb.chat_sqlite.model;


public class ChatModel {
    private int id;
    private int userId;
    private String message;
    private String createdAt;

    public ChatModel() {

    }

    public ChatModel(int userid , String message, String createdAt) {
        this.userId = userid;
        this.message = message;
        this.createdAt = createdAt;
    }

    public ChatModel(int id , int userid, String message, String createdAt) {
        this.id = id;
        this.userId = userid;
        this.message = message;
        this.createdAt = createdAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId(){ return  userId;}

    public void setUserId(int userId) {this.userId = userId;}
}