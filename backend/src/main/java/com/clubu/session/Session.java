package com.clubu.server.session;

import java.util.Random;

public class Session {
    private Long userId;
    private String username;
    private SessionType type;
    private long token;

    public Session(Long userId, String username, SessionType type) {
        this.userId = userId;
        this.username = username;
        this.type = type;
        this.token = new Random().nextLong();
    }

    public Long getUserId() {
        return userId;
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

