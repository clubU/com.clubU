package com.clubu.server.api;

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

import com.clubu.server.dao.StudentDao;
import com.clubu.server.orm.Student;

import io.dropwizard.hibernate.UnitOfWork;

@Path("/student")
public class StudentApi extends AbstractApiBase {

    private StudentDao studentDao;

    public StudentApi() {
        super();
        this.studentDao = StudentDao.getInstance();
    }

    // Start of CORS requests
    @OPTIONS @Produces(MediaType.TEXT_HTML) @Path("/all")
    public Response corsAll() { return newResponse(Response.Status.OK).build(); }
    @OPTIONS @Produces(MediaType.TEXT_HTML)
    public Response cors() { return newResponse(Response.Status.OK).build(); }
    // End of CORS requests

    @UnitOfWork
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/all")
    public Response findAll() {
        List<Student> students = studentDao.findAll();
        return newResponse(Response.Status.OK)
                .entity(students)
                .build();
    }

    @UnitOfWork
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findStudent(
            @QueryParam("username") String username
            ) {
        Student student = studentDao.findByUsername(username);
        if (student != null) {
            return newResponse(Response.Status.OK)
                    .entity(student)
                    .build();
        } else {
            return newResponse(Response.Status.BAD_REQUEST)
                    .entity("{}")
                    .build();
        }
    }

    @UnitOfWork
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createStudent(
            @FormParam("username") String username,
            @FormParam("password") String password,
            @FormParam("firstName") String firstName,
            @FormParam("lastName") String lastName,
            @FormParam("email") String email,
            @FormParam("studentNumber") String studentNumber,
            @FormParam("dateOfBirth") String dateOfBirth,
            @FormParam("yearOfStudy") String yearOfStudy,
            @FormParam("programOfStudy") String programOfStudy
            ) {
        Student student = studentDao.createStudent(
            username, password, firstName, lastName, email,
            studentNumber, dateOfBirth, yearOfStudy, programOfStudy
        );
        if (student != null) {
            return newResponse(Response.Status.OK)
                    .entity(student)
                    .build();
        } else {
            return newResponse(Response.Status.BAD_REQUEST)
                    .entity("{}")
                    .build();
        }
    }

}

