package com.example.Insaaf.Model;

public class Appointment {
    String appointmentId;


    String clientName;
    String lawyerName;
    String clientPhoneNumber;
    String lawyerPhoneNumber;
    String lawyerId;
    String clientId;

    public String getLawyerId() {
        return lawyerId;
    }

    public void setLawyerId(String lawyerId) {
        this.lawyerId = lawyerId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public Appointment() {
    }

    public Appointment(String clientName, String lawyerName, String clientPhoneNumber, String lawyerPhoneNumber) {
        this.clientName = clientName;
        this.lawyerName = lawyerName;
        this.clientPhoneNumber = clientPhoneNumber;
        this.lawyerPhoneNumber = lawyerPhoneNumber;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getLawyerName() {
        return lawyerName;
    }

    public void setLawyerName(String lawyerName) {
        this.lawyerName = lawyerName;
    }

    public String getClientPhoneNumber() {
        return clientPhoneNumber;
    }

    public void setClientPhoneNumber(String clientPhoneNumber) {
        this.clientPhoneNumber = clientPhoneNumber;
    }

    public String getLawyerPhoneNumber() {
        return lawyerPhoneNumber;
    }

    public void setLawyerPhoneNumber(String lawyerPhoneNumber) {
        this.lawyerPhoneNumber = lawyerPhoneNumber;
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

}
