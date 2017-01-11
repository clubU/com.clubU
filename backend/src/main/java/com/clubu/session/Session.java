package com.clubu.server.session;

import java.util.Random;

public class Session {
    private String username;
    private long token;

    public Session(String username) {
        this.username = username;
        this.token = new Random().nextLong();
    }

    public String getUsername() {
        return username;
    }
    private void setUsername(String username) {
        this.username = username;
    }

    public long getToken() {
        return token;
    }
    private void setToken(long token) {
        this.token = token;
    }

}
