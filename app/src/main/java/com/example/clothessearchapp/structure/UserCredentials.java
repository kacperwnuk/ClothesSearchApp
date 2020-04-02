package com.example.clothessearchapp.structure;

public class UserCredentials {
    private String username;
    private String email;

    UserCredentials(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }
}
