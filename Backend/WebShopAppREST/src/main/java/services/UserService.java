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
				if(!user.getBlocked())
				{
					return Response.ok().build();
				}
				return Response.status(Response.Status.UNAUTHORIZED).entity("Account has been blocked.").build();
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
	@PUT
	@Path("/updateWithoutPassword")
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateWithoutPassword(User user)
	{
		UserDAO dao = (UserDAO) ctx.getAttribute("UserDAO");
		
		if(dao.getByUserName(user.getUsername()) != null && !dao.getById(user.getId()).getUsername().equals(user.getUsername()))
		{
			return Response.status(Response.Status.UNAUTHORIZED).entity("Username already exists").build();
		}
		if(dao.update(user) != null)
		{
			return Response.ok().build();
		}
		return Response.status(Response.Status.UNAUTHORIZED).entity("Unexpected error").build();
	}
	@PUT
	@Path("/updatePassword")
	@Produces(MediaType.APPLICATION_JSON)
	public Response updatePassword(User user)
	{
		UserDAO dao = (UserDAO) ctx.getAttribute("UserDAO");
		if(dao.updatePassword(user) != null)
		{
			return Response.ok().build();
		}
		return Response.status(Response.Status.UNAUTHORIZED).entity("Unexpected error").build();
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
	@Path("/blockUser")
	@Produces(MediaType.APPLICATION_JSON)
	public Response blockUser(@QueryParam("id") int id)
	{
		UserDAO dao = (UserDAO) ctx.getAttribute("UserDAO");
		User user = dao.getById(id);
		if(user != null)
		{
			if(!user.getUloga().equals("Administrator"))
			{
				if(dao.blockUser(user) != null)
				{
					return Response.ok().build();
				}
				return Response.status(Response.Status.UNAUTHORIZED).entity("Unexpected error!").build();
			}
			return Response.status(Response.Status.UNAUTHORIZED).entity("Cannot block administrator!").build();
		}
		return Response.status(Response.Status.UNAUTHORIZED).entity("User not found").build();
	}
	@PUT
	@Path("/deleteUser")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteUser(@QueryParam("id") int id)
	{
		UserDAO dao = (UserDAO) ctx.getAttribute("UserDAO");
		User user = dao.getById(id);
		if(user != null)
		{
				if(dao.deleteUser(user) != null)
				{
					return Response.ok().build();
				}
				return Response.status(Response.Status.UNAUTHORIZED).entity("Unexpected error!").build();
		}
		return Response.status(Response.Status.UNAUTHORIZED).entity("User not found").build();
	}

	@PUT
	@Path("/unblockUser")
	@Produces(MediaType.APPLICATION_JSON)
	public Response unblockUser(@QueryParam("id") int id)
	{
		UserDAO dao = (UserDAO) ctx.getAttribute("UserDAO");
		User user = dao.getById(id);
		if(user != null)
		{
			if(!user.getUloga().equals("Administrator"))
			{
				if(dao.unblockUser(user) != null)
				{
					return Response.ok().build();
				}
				return Response.status(Response.Status.UNAUTHORIZED).entity("Unexpected error!").build();
			}
			return Response.status(Response.Status.UNAUTHORIZED).entity("Cannot unblock administrator!").build();
		}
		return Response.status(Response.Status.UNAUTHORIZED).entity("User not found").build();
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
	@GET
	@Path("/getById")
	@Produces(MediaType.APPLICATION_JSON)
	public User getbyId(@QueryParam("id") int id)
	{
		UserDAO dao =(UserDAO) ctx.getAttribute("UserDAO");
		return dao.getById(id);
	}
}
