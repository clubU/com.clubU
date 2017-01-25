package com.clubu.server.session;

import java.util.Random;

public class Session {
    private String username;
    private SessionType type;
    private long token;

    public Session(String username, SessionType type) {
        this.username = username;
        this.type = type;
        this.token = new Random().nextLong();
    }

    public String getUsername() {
        return username;
    }
    public SessionType getType() {
        return type;
    }
    public long getToken() {
        return token;
    }

}

