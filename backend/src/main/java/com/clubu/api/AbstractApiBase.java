package com.clubu.server.api;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import com.clubu.server.session.SessionManager;

public abstract class AbstractApiBase {

    protected final String SESSION_TOKEN_HEADER = "session-token";
    protected SessionManager sessionManager;

    protected AbstractApiBase() {
        this.sessionManager = SessionManager.getInstance();
    }

    protected ResponseBuilder newResponse(Response.Status status) {
        return Response
                .status(status)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "OPTIONS, GET, POST, DELETE, PUT")
                .header("Access-Control-Allow-Headers", "Content-Type")
                .allow("OPTIONS");
    }

}
