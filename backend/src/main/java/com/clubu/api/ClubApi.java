package com.clubu.server.api;

import java.util.Collections;
import java.util.List;

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

import io.dropwizard.hibernate.UnitOfWork;

@Path("/club")
public class ClubApi extends AbstractApiBase {

    private ClubDao clubDao;
    private StudentDao studentDao;

    public ClubApi() {
        super();
        this.clubDao = ClubDao.getInstance();
        this.studentDao = StudentDao.getInstance();
    }

    // Start of CORS requests
    @OPTIONS @Produces(MediaType.TEXT_HTML)
    public Response cors() { return newResponse(Response.Status.OK).build(); }
    @OPTIONS @Produces(MediaType.TEXT_HTML) @Path("/all")
    public Response corsAll() { return newResponse(Response.Status.OK).build(); }
    @OPTIONS @Produces(MediaType.TEXT_HTML) @Path("/{id : \\d+}")
    public Response corsId() { return newResponse(Response.Status.OK).build(); }
    // End of CORS requests

    @UnitOfWork
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(
        @FormParam("username") String username,
        @FormParam("password") String password,
        @FormParam("name") String name,
        @FormParam("email") String email,
        @FormParam("abbreviation") String abbreviation,
        @FormParam("description") String description
        ) {
        Club club = clubDao.createClub(
            username, password, name,
            email, abbreviation, description
        );
        if (club != null) {
            return newResponse(Response.Status.OK)
                    .entity(club)
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
    public Response findBySearchKeyword(@QueryParam("searchKeyword") String searchKeyword) {
        return newResponse(Response.Status.OK)
                .entity(clubDao.findBySearchKeyword(searchKeyword))
                .build();
    }

    @UnitOfWork
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/all")
    public Response findAll() {
        List<Club> clubs = clubDao.findAll();
        return newResponse(Response.Status.OK)
                .entity(clubs)
                .build();
    }

    @UnitOfWork
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id : \\d+}")
    public Response findById(@PathParam("id") long id) {
        return newResponse(Response.Status.OK)
                .entity(clubDao.findById(id))
                .build();
    }

    @UnitOfWork
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/recommendations")
    public Response getRecommendations(@QueryParam("forStudentUsername") String forStudentUsername) {
        Student student = studentDao.findByUsername(forStudentUsername);
        if (student != null) {
            List<Club> clubs = clubDao.findAll();
            clubs.removeAll(student.getClubs());
            Collections.shuffle(clubs);
            return newResponse(Response.Status.OK)
                    .entity(clubs)
                    .build();
        } else {
            return newResponse(Response.Status.BAD_REQUEST)
                    .entity("{}")
                    .build();
        }
    }

}

