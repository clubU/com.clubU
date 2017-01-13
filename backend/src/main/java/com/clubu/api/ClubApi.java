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

import com.clubu.server.dao.ClubDao;
import com.clubu.server.orm.Club;

import io.dropwizard.hibernate.UnitOfWork;

@Path("/club")
public class ClubApi extends AbstractApiBase {

    private ClubDao clubDao;
    
    public ClubApi() {
        super();
        this.clubDao = ClubDao.getInstance();
    }

    // TODO: Implement APIs
}
 
