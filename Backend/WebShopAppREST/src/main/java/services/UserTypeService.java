package services;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import beans.UserType;
import dao.UserTypeDAO;


@Path("/usertype")
public class UserTypeService {
	@Context
	ServletContext ctx;
	
	@PostConstruct
	public void init() {
		if (ctx.getAttribute("UserTypeDAO") == null) {
	    	String contextPath = ctx.getRealPath("");
			ctx.setAttribute("UserTypeDAO", new UserTypeDAO(contextPath));
		}
	}
	
	@GET
	@Path("/getall")
	@Produces(MediaType.APPLICATION_JSON)
	public List<UserType> getall() {
		UserTypeDAO dao = (UserTypeDAO) ctx.getAttribute("UserTypeDAO");
		return dao.getAll();
	}
	@GET
	@Path("/getbyId")
	@Produces(MediaType.APPLICATION_JSON)
	public UserType getbyId(@QueryParam("id") int id)
	{
		UserTypeDAO dao = (UserTypeDAO) ctx.getAttribute("UserTypeDAO");
		return dao.getById(id);
	}
	
}
