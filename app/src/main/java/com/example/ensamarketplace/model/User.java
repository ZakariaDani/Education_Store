package com.example.ensamarketplace.model;


public class User {
    private String name;
    private String email;
    private String branch;
    private String phone;

    public User(){

    }

    public User(String name, String email, String branch, String phone){
        this.name = name;
        this.email = email;
        this.branch = branch;
        this.phone = phone;
    }

    public String getBranch() {
        return branch;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "User{" +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", branch='" + branch + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
