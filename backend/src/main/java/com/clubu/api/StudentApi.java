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

import com.clubu.server.dao.ImageDao;
import com.clubu.server.dao.StudentDao;
import com.clubu.server.orm.Image;
import com.clubu.server.orm.Student;

import io.dropwizard.hibernate.UnitOfWork;

@Path("/student")
public class StudentApi extends AbstractApiBase {

	private ImageDao imageDao;
    private StudentDao studentDao;

    public StudentApi() {
        super();
		this.imageDao = ImageDao.getInstance();
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
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response find(
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
    public Response create(
        @FormParam("username") String username,
        @FormParam("password") String password,
        @FormParam("firstName") String firstName,
        @FormParam("lastName") String lastName,
        @FormParam("email") String email,
        @FormParam("studentNumber") String studentNumber,
        @FormParam("dateOfBirth") String dateOfBirth,
        @FormParam("yearOfStudy") String yearOfStudy,
        @FormParam("programOfStudy") String programOfStudy,
		@FormParam("imageId") Long imageId
    ) {
		Image image = null;
		if (imageId != null) {
			image = imageDao.findById(imageId);
		}
        Student student = studentDao.createStudent(
            username, password, firstName, lastName, email,
            studentNumber, dateOfBirth, yearOfStudy, programOfStudy, image
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

	@UnitOfWork
	@POST
	@Produces(MediaType.APPLICATION_JSON)
    @Path("/{id : \\d+}")
	public Response update(
		@PathParam("id") Long id,
		@FormParam("password") String password,
		@FormParam("firstName") String firstName,
		@FormParam("lastName") String lastName,
		@FormParam("dateOfBirth") String dateOfBirth,
		@FormParam("yearOfStudy") Integer yearOfStudy,
		@FormParam("programOfStudy") String programOfStudy,
		@FormParam("imageId") Long imageId
	) {
		Image image = null;
		if (imageId != null) {
			image = imageDao.findById(imageId);
		}
		Student student = studentDao.update(
			id, password, firstName, lastName,
			dateOfBirth, yearOfStudy, programOfStudy, image
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

    @UnitOfWork
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id : \\d+}")
    public Response findById(@PathParam("id") long id) {
        return newResponse(Response.Status.OK)
                .entity(studentDao.findById(id))
                .build();
    }

}

