package services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import beans.Cart;
import beans.LoginRequest;
import beans.User;
import dao.CartDAO;
import dao.UserDAO;


@Path("/user")
public class UserService {
	@Context
	ServletContext ctx;
	
	@PostConstruct
	public void init() {
		if (ctx.getAttribute("UserDAO") == null) {
	    	String contextPath = ctx.getRealPath("");
			ctx.setAttribute("UserDAO", new UserDAO(contextPath));
		}
		if (ctx.getAttribute("CartDAO") == null) {
			String contextPath = ctx.getRealPath("");
			ctx.setAttribute("CartDAO", new CartDAO(contextPath));
		}
	}
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getByNameAndPassword(LoginRequest loginRequest) {
		UserDAO dao =(UserDAO) ctx.getAttribute("UserDAO");
		String username = loginRequest.getUsername();
		String password = loginRequest.getPassword();
		boolean usernameExists = false;
		User user = dao.getByUserName(username);
		if(user != null)
		{
			usernameExists = true;
		}
		if(usernameExists)
		{
			boolean passwordCorrect = false;
			if(dao.authenticatePassword(username, password) != null)
			{
				passwordCorrect = true;
			}
			if(passwordCorrect)
			{
				return Response.ok().build();
			}
			return Response.status(Response.Status.UNAUTHORIZED).entity("Incorrect password").build();
		}
		else
		{
			return Response.status(Response.Status.UNAUTHORIZED).entity("User does not exist").build();
		}
	}

	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> getAll() throws IOException{
		UserDAO dao =(UserDAO) ctx.getAttribute("UserDAO");
		return dao.getAll();
	}
	@GET
	@Path("/managers")
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> getManagersOrEmployees() throws IOException{
		UserDAO dao = (UserDAO) ctx.getAttribute("UserDAO");
		
		return dao.getFreeManagers();
	}
	@PUT
	@Path("/editManager")
	@Produces(MediaType.APPLICATION_JSON)
	public boolean update(@QueryParam("managerId") int managerId,@QueryParam("factoryId") int factoryId)
	{
		UserDAO dao = (UserDAO) ctx.getAttribute("UserDAO");
		return dao.updateManager(managerId, factoryId);
	}
	@GET
	@Path("/filterByRole")
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> filterByRole(@QueryParam("role") String role)
	{
		UserDAO dao = (UserDAO) ctx.getAttribute("UserDAO");
		
		return dao.getAllByRole(role);
	}
	
	
	@PUT
	@Path("/register")
	@Produces(MediaType.APPLICATION_JSON)
	public Response save(User obj) throws IOException {
		UserDAO dao =(UserDAO) ctx.getAttribute("UserDAO");
		CartDAO cartdao =(CartDAO) ctx.getAttribute("CartDAO");
		int userId = dao.add(obj);
		if (userId != -1)
		{
			if(cartdao.getByUserId(userId) == null)
			{
				Cart cart = new Cart(-1,new ArrayList<Integer>(),0,userId);
				cartdao.add(cart);
			}
			return Response.ok().build();
		}
		return Response.status(Response.Status.UNAUTHORIZED).entity("Username already exists").build();
	}
}
