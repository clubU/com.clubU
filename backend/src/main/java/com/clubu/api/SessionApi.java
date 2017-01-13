package com.clubu.server.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.clubu.server.dao.UserDao;
import com.clubu.server.orm.User;
import com.clubu.server.session.Session;

import io.dropwizard.hibernate.UnitOfWork;

@Path("/session")
public class SessionApi extends AbstractApiBase {

    private UserDao userDao;

    public SessionApi() {
        super();
        this.userDao = UserDao.getInstance();
    }


    @UnitOfWork
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createSession(
            @FormParam("username") String username,
            @FormParam("password") String password
            ) {
        if (userDao.validateCredentials(username, password)) {
            Session session = sessionManager.createSession(username);
            return newResponse(Response.Status.CREATED)
                    .entity(session)
                    .build();
        } else {
            return newResponse(Response.Status.BAD_REQUEST)
                    .entity("{}")
                    .build();
        }
    }

    @UnitOfWork
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findSession(
            @QueryParam("token") String token
            ) {
        Session session = sessionManager.findSessionByToken(Long.parseLong(token));
        if (session != null) {
            return newResponse(Response.Status.OK)
                    .entity(session)
                    .build();
        } else {
            return newResponse(Response.Status.NOT_FOUND)
                    .entity("{}")
                    .build();
        }
    }

}
