package services;

import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import beans.Chocolate;
import dao.ChocolateDAO;

@Path("/chocolate")
public class ChocolateService {
	@Context
	ServletContext ctx;
	
	@PostConstruct
	public void init() {
		if (ctx.getAttribute("ChocolateDAO") == null) {
	    	String contextPath = ctx.getRealPath("");
			ctx.setAttribute("ChocolateDAO", new ChocolateDAO(contextPath));
		}
	}
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Chocolate> getAll() throws IOException{
		ChocolateDAO dao =(ChocolateDAO) ctx.getAttribute("ChocolateDAO");
		return dao.getAll();
	}
	@GET
	@Path("/getByFactoryId")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Chocolate> getAllByFactoryId(@QueryParam("factoryId") int factoryId) {
		ChocolateDAO dao =(ChocolateDAO) ctx.getAttribute("ChocolateDAO");
		return dao.getAllByFactoryId(factoryId);
	}
	@GET
	@Path("/getById")
	@Produces(MediaType.APPLICATION_JSON)
	public Chocolate getById(@QueryParam("id") int id)
	{
		ChocolateDAO dao =(ChocolateDAO) ctx.getAttribute("ChocolateDAO");
		return dao.getById(id);
	}
	
	@PUT
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public boolean save(Chocolate obj) throws IOException {
		ChocolateDAO dao =(ChocolateDAO) ctx.getAttribute("ChocolateDAO");
		return dao.add(obj);
	}
    @POST
    @Path("/update")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Chocolate update(Chocolate updatedChocolate) throws IOException {
        ChocolateDAO dao = (ChocolateDAO) ctx.getAttribute("ChocolateDAO");
        return dao.update(updatedChocolate);
    }
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") int id) {
    	System.out.println(id);
        ChocolateDAO dao = (ChocolateDAO) ctx.getAttribute("ChocolateDAO");
        boolean ret = dao.delete(id);
        System.out.println(ret);
    	if(ret) {
    		return Response.ok().build();
    	}
    	return Response.status(Response.Status.METHOD_NOT_ALLOWED).build();
    }
    
	public ChocolateService() {
	}
}