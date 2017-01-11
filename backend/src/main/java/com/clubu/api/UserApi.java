package com.clubu.server.api;

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

import io.dropwizard.hibernate.UnitOfWork;

@Path("/user")
public class UserApi extends AbstractApiBase {

    private UserDao userDao;
    
    public UserApi() {
        super();
        this.userDao = UserDao.getInstance();
    }

    // Start of CORS requests
    @OPTIONS @Produces(MediaType.TEXT_HTML)
    public Response cors() { return newResponse(Response.Status.OK).build(); }
    // End of CORS requests

    @UnitOfWork
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findUser(
            @QueryParam("username") String username
            ) {
        User user = userDao.findByUsername(username);
        if (user != null) {
            return newResponse(Response.Status.OK)
                    .entity(user)
                    .build();
        } else {
            return newResponse(Response.Status.BAD_REQUEST)
                    .entity("{}")
                    .build();
        }
    }

    @UnitOfWork
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser(
            @FormParam("username") String username,
            @FormParam("password") String password,
            @FormParam("firstName") String firstName,
            @FormParam("lastName") String lastName,
            @FormParam("email") String email,
            @FormParam("studentId") String studentId,
            @FormParam("dateOfBirth") String dateOfBirth,
            @FormParam("yearOfStudy") String yearOfStudy,
            @FormParam("programOfStudy") String programOfStudy
            ) {
        User user = userDao.createUser(
            username, password, firstName, lastName, email,
            studentId, dateOfBirth, yearOfStudy, programOfStudy
        );
        if (user != null) {
            return newResponse(Response.Status.OK)
                    .entity(user)
                    .build();
        } else {
            return newResponse(Response.Status.BAD_REQUEST)
                    .entity("{}")
                    .build();
        }
    }

}
 
