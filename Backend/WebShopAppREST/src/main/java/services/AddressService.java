package services;

import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import beans.Address;
import beans.Location;
import dao.AddressDAO;
import dao.LocationDAO;

@Path("/address")
public class AddressService {
	@Context
	ServletContext ctx;
	
	@PostConstruct
	public void init() {
		if (ctx.getAttribute("AddressDAO") == null) {
	    	String contextPath = ctx.getRealPath("");
			ctx.setAttribute("AddressDAO", new AddressDAO(contextPath));
		}
	}
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Address> getAll() throws IOException{
		AddressDAO dao =(AddressDAO) ctx.getAttribute("AddressDAO");
		return dao.getAll();
	}
	@GET
	@Path("/GetById")
	@Produces(MediaType.APPLICATION_JSON)
	public Address getById(@QueryParam("id") int id) {
		AddressDAO dao =(AddressDAO) ctx.getAttribute("AddressDAO");
		return dao.getById(id);
	}
	
	@PUT
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public int save(Address obj) throws IOException {
		AddressDAO dao =(AddressDAO) ctx.getAttribute("AddressDAO");
		return dao.add(obj); //gets back address id 
	}
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public boolean delete(@PathParam("id") int id) {
    	System.out.println(id);
    	AddressDAO dao = (AddressDAO) ctx.getAttribute("AddressDAO");
    	return dao.delete(id);
    }
    
	public AddressService() {
	}
}
