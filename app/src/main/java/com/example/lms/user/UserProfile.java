package com.example.lms.user;

/*adding user details using this
* this is like a helper class
* eg:- getting details and sending to databse
* getting details and showing in profile activity*/
public class UserProfile {
    public String userDOB;
    public String userEmail;
    public String userName;
    public String userRegistrationNumber;
    public String userPassword;

    public UserProfile(){
    }

    public UserProfile(String userDOB, String userEmail, String userName, String userRegistrationNumber, String userPassword) {
        this.userDOB = userDOB;
        this.userEmail = userEmail;
        this.userName = userName;
        this.userRegistrationNumber = userRegistrationNumber;
        this.userPassword = userPassword;
    }

    public String getUserDOB() {
        return userDOB;
    }

    public void setUserDOB(String userDOB) {
        this.userDOB = userDOB;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserRegistrationNumber() {
        return userRegistrationNumber;
    }

    public void setUserRegistrationNumber(String userRegistrationNumber) {
        this.userRegistrationNumber = userRegistrationNumber;
    }
}
