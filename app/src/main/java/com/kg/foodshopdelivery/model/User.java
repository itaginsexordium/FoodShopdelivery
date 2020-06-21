package com.kg.foodshopdelivery.model;

public class User {
    private String Name;
    private String password;

    public User() {
    }

    public User(String name, String password) {
        Name = name;
        this.password = password;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
