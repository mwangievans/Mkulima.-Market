package com.example.mkulima.Model;

public class User {
    private String uid,userName, email, phone, natIdNo, password, role,imageUrl;
    public User() {

    }

    public User(String uid,String userName, String email, String phone, String natIdNo, String password, String role,String imageUrl) {
        this.uid=uid;
        this.userName = userName;
        this.email = email;
        this.phone = phone;
        this.natIdNo = natIdNo;
        this.password = password;
        this.role = role;
        this.imageUrl = imageUrl;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNatIdNo() {
        return natIdNo;
    }

    public void setNatIdNo(String natIdNo) {
        this.natIdNo = natIdNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
