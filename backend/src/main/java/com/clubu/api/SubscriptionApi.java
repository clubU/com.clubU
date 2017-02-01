package com.clubu.server.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.clubu.server.dao.ClubDao;
import com.clubu.server.dao.StudentDao;
import com.clubu.server.orm.Club;
import com.clubu.server.orm.Student;

import io.dropwizard.hibernate.UnitOfWork;

@Path("/subscription")
public class SubscriptionApi extends AbstractApiBase {

    private ClubDao clubDao;
    private StudentDao studentDao;

    public SubscriptionApi() {
        super();
        this.clubDao = ClubDao.getInstance();
        this.studentDao = StudentDao.getInstance();
    }

    // Start of CORS requests
    @OPTIONS @Produces(MediaType.TEXT_HTML)
    public Response cors() { return newResponse(Response.Status.OK).build(); }
    // End of CORS requests

    @UnitOfWork
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(
        @FormParam("studentId") Long studentId,
        @FormParam("studentUsername") String studentUsername,
        @FormParam("clubId") Long clubId
        ) {
        Student student = null;
        Club club = null;
        if (studentId != null) {
            student = studentDao.findById(studentId);
        } else if (studentUsername != null) {
            student = studentDao.findByUsername(studentUsername);
        }
        if (clubId != null) {
            club = clubDao.findById(clubId);
        }
        if (student == null || club == null) {
            return newResponse(Response.Status.BAD_REQUEST)
                    .entity("{}")
                    .build();
        }
        return newResponse(Response.Status.CREATED)
                .entity(studentDao.addClub(student, club))
                .build();
    }

}
