package services;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.json.Json;
import javax.json.JsonObject;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import Utilities.JWTDecoder;
import Utilities.Token;
import Utilities.UserSusDTO;
import beans.Cart;
import beans.Chocolate;
import beans.Comment;
import beans.JWTUser;
import beans.LoginRequest;
import beans.Purchase;
import beans.User;
import dao.CartDAO;
import dao.CommentDAO;
import dao.PurchaseDAO;
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
		if (ctx.getAttribute("UserDAO") == null) {
			String contextPath = ctx.getRealPath("");
			ctx.setAttribute("UserDAO", new UserDAO(contextPath));
		}
		if (ctx.getAttribute("PurchaseDAO") == null) {
			String contextPath = ctx.getRealPath("");
			ctx.setAttribute("PurchaseDAO", new PurchaseDAO(contextPath));
		}
	}
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getByNameAndPassword(LoginRequest loginRequest) {
		UserDAO dao =(UserDAO) ctx.getAttribute("UserDAO");
		String username = loginRequest.getUsername();
		String password = loginRequest.getPassword();
		User user = dao.getByUserName(username);
		if(user == null)
		{
			return Response.status(Response.Status.UNAUTHORIZED).entity("User does not exist").build();
		}
		if(!dao.authenticatePassword(username, password))
		{
			return Response.status(Response.Status.UNAUTHORIZED).entity("Incorrect password").build();
		}
		if(user.getBlocked())
		{
			return Response.status(Response.Status.UNAUTHORIZED).entity("Account has been blocked.").build();
		}
		
		try {
			return Response.ok(JWTDecoder.createToken(new JWTUser(user.getId(), user.getUsername(), user.getUloga().toString()), 43200000L)).build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(403).build();
	}
	@POST
    @Path("/jwt/decode")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JWTUser getJWTUser(Token token) throws Exception {
        return JWTDecoder.verifyToken(token.token);
    }
	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	public List<UserSusDTO> getAll() throws IOException {
	    List<UserSusDTO> retlist = new ArrayList<>();
	    UserDAO dao = (UserDAO) ctx.getAttribute("UserDAO");
	    PurchaseDAO purchaseDAO = (PurchaseDAO) ctx.getAttribute("PurchaseDAO");

	    List<Purchase> purchases = purchaseDAO.getAll();
	    List<User> users = dao.getAll();

	    for (User user : users) {
	        List<Purchase> filteredPurchases = purchases.stream()
	                .filter(p -> p.getUserId() == user.getId() && p.getStatus().equals("Otkazano"))
	                .collect(Collectors.toList());
	        System.out.println(filteredPurchases.size());
	        if (filteredPurchases.size() > 0) {
	            System.out.println("BOZE IMA");
		        retlist.add(new UserSusDTO(user, true));
	        } else {
	            retlist.add(new UserSusDTO(user, false));
	        }
	    }
	    return retlist;
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
		obj.setId(userId);
		if (userId != -1)
		{
			if(obj.getUloga().equals("Customer")) {
				if(cartdao.getByUserId(userId) == null)
				{
					Cart cart = new Cart(-1,new HashMap<Integer,Integer>(),0,userId);
					cartdao.add(cart);
				}
			}
			return Response.ok().entity(obj).build();
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
	
	@GET
    @Path("/getManagerFactory")
    public Response getManagerFactory(@QueryParam("userId") int userId) {
        	System.out.println(userId);
            // Assume UserService and FactoryService are services that handle business logic
            UserDAO userDAO =(UserDAO) ctx.getAttribute("UserDAO");

            // Fetch the user by userId
            User user = userDAO.getById(userId);
            if (user == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("User not found").build();
            }

            int factoryId=user.getFactoryId();
            if (factoryId == -1) {
                return Response.status(Response.Status.NOT_FOUND).entity("Factory not found for the manager").build();
            }
            JsonObject responseObject = Json.createObjectBuilder()
                .add("factoryId", factoryId)
                .build();
            return Response.ok(responseObject).build();
            //return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error retrieving factory information").build();
    }
}
