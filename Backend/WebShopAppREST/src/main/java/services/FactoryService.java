package services;

import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import beans.Factory;
import dao.FactoryDAO;

@Path("/factory")
public class FactoryService {
	@Context
	ServletContext ctx;
	
	@PostConstruct
	public void init() {
		if (ctx.getAttribute("FactoryDAO") == null) {
	    	String contextPath = ctx.getRealPath("");
			ctx.setAttribute("FactoryDAO", new FactoryDAO(contextPath));
		}
	}
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Factory> getAll() throws IOException{
		FactoryDAO dao =(FactoryDAO) ctx.getAttribute("FactoryDAO");
		return dao.getAll();
	}
	@GET
	@Path("/GetById")
	@Produces(MediaType.APPLICATION_JSON)
	public Factory getById(@QueryParam("id") int id) {
		FactoryDAO dao =(FactoryDAO) ctx.getAttribute("FactoryDAO");
		return dao.GetById(id);
	}
	
	@PUT
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public int save(Factory obj) throws IOException {
		FactoryDAO dao =(FactoryDAO) ctx.getAttribute("FactoryDAO");
		return dao.add(obj);
	}
    @PATCH
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Factory update(Factory updatedFactory) throws IOException {
    	FactoryDAO dao = (FactoryDAO) ctx.getAttribute("FactoryDAO");
        return dao.update(updatedFactory);
    }
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public boolean delete(@PathParam("id") int id) {
    	System.out.println(id);
    	FactoryDAO dao = (FactoryDAO) ctx.getAttribute("FactoryDAO");
    	return dao.delete(id);
    }
    
	public FactoryService() {
	}

}
