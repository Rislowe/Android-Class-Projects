package com.example.loginscreen;

public class LoginManager {

    private final String username;
    private final String password;

    public LoginManager(String username, String password)
    {
        this.username = username;
        this.password = password;
    }

    public boolean hasValidCredentials()
    {
        boolean result = username.equals("admin") && password.equals("admin");
        return result;
    }
}
