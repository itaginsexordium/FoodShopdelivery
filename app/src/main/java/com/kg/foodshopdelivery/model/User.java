package com.kg.foodshopdelivery.model;

public class User {
    private String Name;
    private String password;
    private String Phone;

    public User() {
    }

    public User(String name, String password, String phone) {
        Name = name;
        this.password = password;
        Phone = phone;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
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
