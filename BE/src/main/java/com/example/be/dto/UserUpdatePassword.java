package com.example.be.dto;

public class UserUpdatePassword {
    private String email;

    private String oldpass;

    private String password;

    public UserUpdatePassword() {
    }

    public UserUpdatePassword(String email, String oldpass, String password) {
        this.email = email;
        this.oldpass = oldpass;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOldpass() {
        return oldpass;
    }

    public void setOldpass(String oldpass) {
        this.oldpass = oldpass;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
