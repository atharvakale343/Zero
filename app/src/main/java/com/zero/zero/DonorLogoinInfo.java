package com.zero.zero;

public class DonorLogoinInfo {
    public String userName,password;

    DonorLogoinInfo(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public DonorLogoinInfo() {

    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String toString() {
        return ("username: "+ userName + " password: " + password);
    }



}
