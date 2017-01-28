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

import com.clubu.server.dao.ClubDao;
import com.clubu.server.dao.StudentDao;
import com.clubu.server.orm.Club;
import com.clubu.server.orm.Student;
import com.clubu.server.session.Session;
import com.clubu.server.session.SessionType;

import io.dropwizard.hibernate.UnitOfWork;

@Path("/session")
public class SessionApi extends AbstractApiBase {

    private ClubDao clubDao;
    private StudentDao studentDao;

    public SessionApi() {
        super();
        this.studentDao = StudentDao.getInstance();
        this.clubDao = ClubDao.getInstance();
    }

    @UnitOfWork
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createSession(
            @FormParam("username") String username,
            @FormParam("password") String password,
            @FormParam("type") int type
            ) {
        if (type == SessionType.STUDENT.getTypeValue()) {
            if (studentDao.validateCredentials(username, password)) {
                Session session = sessionManager.createSession(username, SessionType.STUDENT);
                return newResponse(Response.Status.CREATED)
                        .entity(session)
                        .build();
            }
        } else if (type == SessionType.CLUB.getTypeValue()) {
            if (clubDao.validateCredentials(username, password)) {
                Session session = sessionManager.createSession(username, SessionType.CLUB);
                return newResponse(Response.Status.CREATED)
                        .entity(session)
                        .build();
            }
        }
        return newResponse(Response.Status.BAD_REQUEST)
                .entity("{}")
                .build();
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

