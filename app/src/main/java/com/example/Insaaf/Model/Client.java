package com.example.Insaaf.Model;



public class Client {
    String Name;
    String contact_no;

    String uname;
    String email;

    public Client(String Nmae,String contact_no,String uname, String email) {
        this.uname = uname;
        this.email = email;
        this.Name=Name;
        this.contact_no=contact_no;
    }
    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getContact_no() {
        return contact_no;
    }

    public void setContact_no(String contact_no) {
        this.contact_no = contact_no;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
