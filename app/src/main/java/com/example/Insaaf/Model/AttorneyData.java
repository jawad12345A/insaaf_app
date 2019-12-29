package com.example.Insaaf.Model;


public class AttorneyData {

    String id;
    String name;
    String email;
    String contact_no;
    String bc_id;
    String city;
    String courts;
    String address;
    String services;
    String bio;
    String photo_url;
    String password;
    String totalCaseFought;
    String totalCaseWin;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public AttorneyData() {

    }


    public AttorneyData(String name, String email, String contact_no, String bc_id, String city, String courts, String address, String services, String bio, String photo_url, String password, String caseFought, String caseWin) {
        this.name = name;
        this.email = email;
        this.contact_no = contact_no;
        this.bc_id = bc_id;
        this.city = city;
        this.courts = courts;
        this.address = address;
        this.services = services;
        this.bio = bio;
        this.photo_url = photo_url;
        this.password = password;
        id = "  ";
        this.totalCaseFought = caseFought;
        this.totalCaseWin = caseWin;

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

    public String getContact_no() {
        return contact_no;
    }

    public void setContact_no(String contact_no) {
        this.contact_no = contact_no;
    }

    public String getBc_id() {
        return bc_id;
    }

    public void setBc_id(String bc_id) {
        this.bc_id = bc_id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCourts() {
        return courts;
    }

    public void setCourts(String courts) {
        this.courts = courts;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getPhoto_url() {
        return photo_url;
    }

    public void setPhoto_url(String photo_url) {
        this.photo_url = photo_url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTotalCaseFought() {
        return totalCaseFought;
    }

    public void setTotalCaseFought(String totalCaseFought) {
        this.totalCaseFought = totalCaseFought;
    }

    public String getTotalCaseWin() {
        return totalCaseWin;
    }

    public void setTotalCaseWin(String totalCaseWin) {
        this.totalCaseWin = totalCaseWin;
    }

}