package com.clubu.server.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.clubu.server.dao.ClubDao;
import com.clubu.server.orm.Club;
import com.clubu.server.dao.EventDao;
import com.clubu.server.orm.Event;

import io.dropwizard.hibernate.UnitOfWork;

@Path("/event")
public class EventApi extends AbstractApiBase {

    private ClubDao clubDao;
    private EventDao eventDao;

    public EventApi() {
        super();
        this.clubDao = ClubDao.getInstance();
        this.eventDao = EventDao.getInstance();
    }

    // Start of CORS requests
    @OPTIONS @Produces(MediaType.TEXT_HTML)
    public Response cors() { return newResponse(Response.Status.OK).build(); }
    @OPTIONS @Produces(MediaType.TEXT_HTML) @Path("/{id : \\d+}")
    public Response corsId() { return newResponse(Response.Status.OK).build(); }
    // End of CORS requests

    @UnitOfWork
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(
        @FormParam("clubId") long clubId,
        @FormParam("title") String title,
        @FormParam("time") long date,
        @FormParam("location") String location,
        @FormParam("description") String description
        ) {
        Club club = clubDao.findById(clubId);
        if (club == null) {
            return newResponse(Response.Status.BAD_REQUEST)
                    .entity("{}")
                    .build();
        }
        Event event = eventDao.create(
            club, title, date, location, description
        );
        if (event != null) {
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
    @Path("/{id : \\d+}")
    public Response findById(@PathParam("id") long id) {
        return newResponse(Response.Status.OK)
                .entity(eventDao.findById(id))
                .build();
    }

}

