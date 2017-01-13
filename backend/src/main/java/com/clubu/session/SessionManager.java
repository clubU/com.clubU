package com.clubu.server.session;

import java.util.List;
import java.util.ArrayList;

public class SessionManager {

    private static SessionManager instance = null;
    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    private List<Session> sessions;

    private SessionManager() {
        this.sessions = new ArrayList<Session>();
    }

    public boolean isValidToken(long token) {
        for (Session s : sessions) {
            if (s.getToken() == token) {
                return true;
            }
        }
        return false;
    }

    public Session createSession(String username) {
        Session session = new Session(username);
        sessions.add(session);
        return session;
    }

    public Session findSessionByToken(long token) {
        for (Session s : sessions) {
            if (s.getToken() == token) {
                return s;
            }
        }
        return null;
    }

    public void deleteSessionByToken(long token) {
        Session toRemove = null;
        for (Session s : sessions) {
            if (s.getToken() == token) {
                toRemove = s;
                break;
            }
        }
        sessions.remove(toRemove);
    }

}

