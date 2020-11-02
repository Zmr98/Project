package com.example.lms.admin.adminactivity;

/*This is use to get the admin details for make the
* Login page
* Profile page
* and initializing data to firebase database*/
public class AdminProfile {
    private String adminDOB;
    private String adminEmail;
    private String adminName;
    private String adminIdNumber;
    private String adminPassword;

    public AdminProfile() {
    }

    public AdminProfile(String adminDOB, String adminEmail, String adminName, String adminIdNumber, String adminPassword) {
        this.adminDOB = adminDOB;
        this.adminEmail = adminEmail;
        this.adminName = adminName;
        this.adminIdNumber = adminIdNumber;
        this.adminPassword = adminPassword;
    }

    public String getAdminDOB() {
        return adminDOB;
    }

    public void setAdminDOB(String adminDOB) {
        this.adminDOB = adminDOB;
    }

    public String getAdminEmail() {
        return adminEmail;
    }

    public void setAdminEmail(String adminEmail) {
        this.adminEmail = adminEmail;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getAdminIdNumber() {
        return adminIdNumber;
    }

    public void setAdminIdNumber(String adminIdNumber) {
        this.adminIdNumber = adminIdNumber;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }
}
