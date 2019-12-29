package com.example.Insaaf.Model;

public class ClientData {
    String name;
    String email;
    String contact;
    String password;
    String clientImage;
    String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClientImage() {
        return clientImage;
    }

    public void setClientImage(String clientImage) {
        this.clientImage = clientImage;
    }

    public ClientData() {
    }

    public ClientData(String name, String email, String contact, String password, String clientImage) {
        this.name = name;
        this.email = email;
        this.contact = contact;
        this.password = password;
        this.clientImage = clientImage;
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

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
