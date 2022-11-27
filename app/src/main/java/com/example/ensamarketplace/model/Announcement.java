package com.example.ensamarketplace.model;

public class Announcement {
    private String titre;
    private String type;
    private String image;
    private String branch;
    private String phone;
    private String description;
    private String price;
    private String userOwner;
    private int id;
    public Announcement(){}

    public Announcement(String titre, String type, String image, String branch, String phone, String description, String price, String userOwner) {
        this.titre = titre;
        this.type = type;
        this.image = image;
        this.branch = branch;
        this.phone = phone;
        this.description = description;
        this.price = price;
        this.userOwner = userOwner;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUserOwner() {
        return userOwner;
    }

    public void setUserOwner(String userOwner) {
        this.userOwner = userOwner;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Announcement{" +
                "titre='" + titre + '\'' +
                ", type='" + type + '\'' +
                ", image='" + image + '\'' +
                ", branch='" + branch + '\'' +
                ", phone='" + phone + '\'' +
                ", description='" + description + '\'' +
                ", userOwner='" + userOwner + '\'' +
                '}';
    }
}