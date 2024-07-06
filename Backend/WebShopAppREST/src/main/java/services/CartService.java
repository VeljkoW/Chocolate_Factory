package services;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import Utilities.CheckoutDTO;
import Utilities.ChocolateCartItem;
import Utilities.JWTDecoder;
import Utilities.Token;
import Utilities.UpdateCartRequest;
import beans.Cart;
import beans.Chocolate;
import beans.JWTUser;
import beans.Purchase;
import beans.User;
import dao.CartDAO;
import dao.ChocolateDAO;
import dao.PurchaseDAO;
import dao.UserDAO;

@Path("/cart")
public class CartService {
	@Context
	ServletContext ctx;
	
	@PostConstruct
	public void init() {
		if (ctx.getAttribute("CartDAO") == null) {
			String contextPath = ctx.getRealPath("");
			ctx.setAttribute("CartDAO", new CartDAO(contextPath));
		}
		if (ctx.getAttribute("ChocolateDAO") == null) {
			String contextPath = ctx.getRealPath("");
			ctx.setAttribute("ChocolateDAO", new ChocolateDAO(contextPath));
		}
		if (ctx.getAttribute("PurchaseDAO") == null) {
			String contextPath = ctx.getRealPath("");
			ctx.setAttribute("PurchaseDAO", new PurchaseDAO(contextPath));
		}
		if (ctx.getAttribute("UserDAO") == null) {
			String contextPath = ctx.getRealPath("");
			ctx.setAttribute("UserDAO", new UserDAO(contextPath));
		}
	}
	
	@POST
	@Path("/add")
	@Produces(MediaType.APPLICATION_JSON)
	public Response addToCart(ChocolateCartItem item) throws Exception {
		ChocolateDAO chocolateDAO=(ChocolateDAO)ctx.getAttribute("ChocolateDAO");
		CartDAO dao = (CartDAO)ctx.getAttribute("CartDAO");
		JWTUser userToken = JWTDecoder.verifyToken(item.token);
		if(userToken!= null) {
			Cart cart = dao.getByUserId(userToken.id);
			if(cart!=null) {
			HashMap<Integer,Integer> chocolates = cart.getChocolates();
			System.out.println(chocolates.size());
			if(cart.getChocolates().size()==0) {
				chocolates.put(item.chocolate.getId(), item.quantity);
				cart.setChocolates(chocolates);
				
				Chocolate chocolate =chocolateDAO.getById(item.chocolate.getId());
				chocolate.setStock(chocolate.getStock()-item.quantity);
				chocolateDAO.update(chocolate);
				return Response.ok().build();
			}
			else {
				for (int cartChocolate : chocolates.keySet()) {
					
	                if (chocolateDAO.getById(cartChocolate).getFactoryId() != item.chocolate.getFactoryId()) {
	                    return Response.status(409)
	                            .entity("Clear your cart, you have chocolates from a different factory.")
	                            .build();
	                }
	            }
				if(chocolates.containsKey(item.chocolate)) {
					int newQuantity = chocolates.get(item.chocolate)+item.quantity;
					chocolates.put(item.chocolate.getId(), newQuantity);
					
					Chocolate chocolate =chocolateDAO.getById(item.chocolate.getId());
					chocolate.setStock(chocolate.getStock()-item.quantity);
					chocolateDAO.update(chocolate);
					return Response.ok().build();
				}
                chocolates.put(item.chocolate.getId(), item.quantity);
				cart.setChocolates(chocolates);
				
				Chocolate chocolate =chocolateDAO.getById(item.chocolate.getId());
				chocolate.setStock(chocolate.getStock()-item.quantity);
				chocolateDAO.update(chocolate);
			}}
        }
		return Response.ok().build();
	}
	@POST
	@Path("/fetch")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response fetchCartItems(Token token) throws Exception {
	    CartDAO dao = (CartDAO) ctx.getAttribute("CartDAO");
	    ChocolateDAO chocolateDAO = (ChocolateDAO) ctx.getAttribute("ChocolateDAO");
	    JWTUser userToken = JWTDecoder.verifyToken(token.token);
	    
	    if (userToken != null) {
	    	System.out.println(userToken.getId());
	        Cart cart = dao.getByUserId(userToken.getId());
	        if(cart!=null) {
	        	HashMap<Chocolate,Integer> ret = new HashMap<Chocolate,Integer>();
	        	for(int chocolateId : cart.getChocolates().keySet())
	        	{
	        		Chocolate c = chocolateDAO.getById(chocolateId);
	        		if (c != null) {
	                    int quantity = cart.getChocolates().get(chocolateId);
	                    ret.put(c, quantity);
	                }
	        	}
	        	return Response.ok().entity(ret).build();
	        }
		    return Response.status(416).build();
	    }
	    return Response.status(419).build();
	}
	@PUT
    @Path("/update/{itemId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCartItem(@PathParam("itemId") int itemId, UpdateCartRequest token) {
        CartDAO dao = (CartDAO) ctx.getAttribute("CartDAO");
        JWTUser userToken;
		try {
			userToken = JWTDecoder.verifyToken(token.getToken());
		
		ChocolateDAO chocolateDAO=(ChocolateDAO)ctx.getAttribute("ChocolateDAO");
        if (userToken != null) {
            Cart cart = dao.getByUserId(userToken.id);
            
            if (cart != null) {
            	cart.getChocolates().put(itemId, token.getQuantity());
                dao.update(cart);

                return Response.ok().entity("Cart item updated successfully").build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("Cart item not found").build();
            }
        } 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(Response.Status.UNAUTHORIZED).build();
    }
	@DELETE
    @Path("/delete/{itemId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCartItem(@PathParam("itemId") int chocolateId,Token token) {
        CartDAO dao = (CartDAO) ctx.getAttribute("CartDAO");
        JWTUser userToken;
		try {
			userToken = JWTDecoder.verifyToken(token.token);
		
		ChocolateDAO chocolateDAO=(ChocolateDAO)ctx.getAttribute("ChocolateDAO");
        if (userToken != null) {
            Cart cart = dao.getByUserId(userToken.id);
            
            if (cart != null) {
            	HashMap<Integer,Integer> chocolates = cart.getChocolates();
            	chocolates.remove(chocolateId);
            	cart.setChocolates(chocolates);
                dao.update(cart);
                return Response.ok().entity("Chocolate removed successfully").build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("Cart not found").build();
            }
        } 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(Response.Status.UNAUTHORIZED).build();
    }
	
	@POST
	@Path("/checkout")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response checkout(CheckoutDTO token) throws Exception {
	    CartDAO dao = (CartDAO) ctx.getAttribute("CartDAO");
	    PurchaseDAO purchaseDAO=(PurchaseDAO)ctx.getAttribute("PurchaseDAO");
		ChocolateDAO chocolateDAO=(ChocolateDAO)ctx.getAttribute("ChocolateDAO");
	    UserDAO userDAO=(UserDAO)ctx.getAttribute("UserDAO");
	    JWTUser userToken = JWTDecoder.verifyToken(token.token);
	    try {
	        userToken = JWTDecoder.verifyToken(token.token);
	        if (userToken != null) {
	            Cart cart = dao.getByUserId(userToken.getId());
	            if (cart != null) {
	            	Chocolate chocolate = new Chocolate();
	            	for(int i : cart.getChocolates().keySet()) {
	            		chocolate = chocolateDAO.getById(i);
	            		break;
	            	}
	            	if(chocolate!=null) {
	            	Integer factoryId=chocolate.getFactoryId();
	            	
	            	double totalPrice = 0.0;
	                for (Map.Entry<Integer, Integer> entry : cart.getChocolates().entrySet()) {
	                    int chocolateId = entry.getKey();
	                    int quantity = entry.getValue();
	                    
	                    Chocolate c = chocolateDAO.getById(chocolateId);
	                    
	                    if (c != null) {
	                        totalPrice += chocolate.getPrice() * quantity;
	                    } else {
	                        System.err.println("Chocolate with ID " + chocolateId + " not found.");
	                    }
	                }
	            	purchaseDAO.add(new Purchase(-1, generateUniqueId(), cart.getChocolates(), factoryId, "01/05/2022", totalPrice, userToken.id, "Obrada",false));
	                cart.setChocolates(new HashMap<>());
	                dao.update(cart);
	                
	                User user = userDAO.getById(userToken.id);
	                user.setPoints(user.getPoints()+(totalPrice/1000*133));
	                userDAO.update(user);
	                return Response.ok().entity("Purchase successful").build();
	            	}
	            } else {
	                return Response.status(Response.Status.NOT_FOUND).entity("Cart not found").build();
	            }
	        }
	    } catch (Exception e){
	        e.printStackTrace();
	    }
	    return Response.status(Response.Status.UNAUTHORIZED).build();
	}
	private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int ID_LENGTH = 10;
    private static final SecureRandom RANDOM = new SecureRandom();

    public static String generateUniqueId() {
        StringBuilder uniqueId = new StringBuilder(ID_LENGTH);
        for (int i = 0; i < ID_LENGTH; i++) {
            int index = RANDOM.nextInt(CHARACTERS.length());
            uniqueId.append(CHARACTERS.charAt(index));
        }
        return uniqueId.toString();
    }
}