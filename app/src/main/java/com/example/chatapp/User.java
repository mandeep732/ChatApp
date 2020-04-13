package com.example.chatapp;

public class User {
    private String id , name,imageURL, email;

    public  User(){}
    public User(String id, String name, String email,String imageURL) {
        this.id = id;
        this.imageURL = imageURL;
        this.name = name;
        this.email =email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
