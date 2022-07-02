package com.example.entertainment_trivia;

public class Account {

    private String userName;
    private String email;
    private String password;
    private String score;


    public Account(String userName, String email, String password, String score){
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.score = score;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
