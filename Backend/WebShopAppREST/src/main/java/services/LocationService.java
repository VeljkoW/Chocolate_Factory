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
import beans.Location;
import dao.FactoryDAO;
import dao.LocationDAO;


@Path("/location")
public class LocationService {
	@Context
	ServletContext ctx;
	
	@PostConstruct
	public void init() {
		if (ctx.getAttribute("LocationDAO") == null) {
	    	String contextPath = ctx.getRealPath("");
			ctx.setAttribute("LocationDAO", new LocationDAO(contextPath));
		}
	}
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Location> getAll() throws IOException{
		LocationDAO dao =(LocationDAO) ctx.getAttribute("LocationDAO");
		return dao.getAll();
	}
	@GET
	@Path("/GetById")
	@Produces(MediaType.APPLICATION_JSON)
	public Location getById(@QueryParam("id") int id) {
		LocationDAO dao =(LocationDAO) ctx.getAttribute("LocationDAO");
		return dao.getById(id);
	}
	
	@PUT
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public int save(Location obj) throws IOException {
		LocationDAO dao =(LocationDAO) ctx.getAttribute("LocationDAO");
		return dao.add(obj);
	}
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public boolean delete(@PathParam("id") int id) {
    	System.out.println(id);
    	LocationDAO dao = (LocationDAO) ctx.getAttribute("LocationDAO");
    	return dao.delete(id);
    }
    
	public LocationService() {
	}
}
