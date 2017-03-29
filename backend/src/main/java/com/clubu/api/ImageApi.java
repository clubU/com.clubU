package com.clubu.server.api;

import java.io.InputStream;
import java.io.IOException;

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

import com.clubu.server.dao.ImageDao;
import com.clubu.server.orm.Image;

import org.glassfish.jersey.media.multipart.FormDataParam;

import io.dropwizard.hibernate.UnitOfWork;

import org.apache.commons.io.IOUtils;

@Path("/image")
public class ImageApi extends AbstractApiBase {

    private ImageDao imageDao;

    public ImageApi() {
        super();
        this.imageDao = ImageDao.getInstance();
    }

    // Start of CORS requests
    @OPTIONS @Produces(MediaType.TEXT_HTML)
    public Response cors() { return newResponse(Response.Status.OK).build(); }
    @OPTIONS @Produces(MediaType.TEXT_HTML) @Path("/{id : \\d+}")
    public Response corsId() { return newResponse(Response.Status.OK).build(); }
    // End of CORS requests

    //@UnitOfWork
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(@FormDataParam("file") InputStream fileContent) {
/*           
        try {
            byte[] content = IOUtils.toByteArray(fileContent);
            Image image = imageDao.create(content);
            if (image != null) {
                return newResponse(Response.Status.OK)
                        .entity(image)
                        .build();
            } else {
                return newResponse(Response.Status.BAD_REQUEST)
                        .entity("{}")
                        .build();
            }
        } catch (IOException e) {
            return newResponse(Response.Status.BAD_REQUEST)
                    .entity("{}")
                    .build();
        }
*/
            return newResponse(Response.Status.BAD_REQUEST)
                    .entity("{}")
                    .build();
    }
    
    @UnitOfWork
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id : \\d+}")
    public Response findById(@PathParam("id") long id) {
        return newResponse(Response.Status.OK)
                .entity(imageDao.findById(id))
                .build();
    }

}

