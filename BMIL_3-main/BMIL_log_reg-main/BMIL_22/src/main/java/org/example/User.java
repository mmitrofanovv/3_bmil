package org.example;

public class User { //класс пользователя
    private String login;
    private String password;

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    // Method to update password
    public void setPassword(String password) {
        this.password = password;
    }
}