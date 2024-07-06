package services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import Utilities.PurchaseDTO;
import beans.Chocolate;
import beans.Factory;
import beans.Purchase;
import beans.User;
import dao.ChocolateDAO;
import dao.FactoryDAO;
import dao.PurchaseDAO;
import dao.UserDAO;

@Path("/purchase")
public class PurchaseService {
    @Context
    ServletContext ctx;

    @PostConstruct
    public void init() {
        if (ctx.getAttribute("PurchaseDAO") == null) {
            String contextPath = ctx.getRealPath("");
            ctx.setAttribute("PurchaseDAO", new PurchaseDAO(contextPath));
        }
        if (ctx.getAttribute("ChocolateDAO") == null) {
            String contextPath = ctx.getRealPath("");
            ctx.setAttribute("ChocolateDAO", new ChocolateDAO(contextPath));
        }
        if (ctx.getAttribute("FactoryDAO") == null) {
            String contextPath = ctx.getRealPath("");
            ctx.setAttribute("FactoryDAO", new FactoryDAO(contextPath));
        }
        if (ctx.getAttribute("UserDAO") == null) {
            String contextPath = ctx.getRealPath("");
            ctx.setAttribute("UserDAO", new UserDAO(contextPath));
        }
    }

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Purchase> getAll() throws IOException {
        PurchaseDAO dao = (PurchaseDAO) ctx.getAttribute("PurchaseDAO");
        return dao.getAll();
    }

    @GET
    @Path("/getById")
    @Produces(MediaType.APPLICATION_JSON)
    public Purchase getById(@QueryParam("id") int id) {
        PurchaseDAO dao = (PurchaseDAO) ctx.getAttribute("PurchaseDAO");
        return dao.getById(id);
    }
    
    @GET
    @Path("/getByUserId")
    @Produces(MediaType.APPLICATION_JSON)
    public List<PurchaseDTO> getPurchasesByUserId(@QueryParam("userId") int userId) {
        PurchaseDAO purchaseDAO = (PurchaseDAO) ctx.getAttribute("PurchaseDAO");
        ChocolateDAO chocolateDAO = (ChocolateDAO) ctx.getAttribute("ChocolateDAO");
        FactoryDAO factoryDAO = (FactoryDAO) ctx.getAttribute("FactoryDAO");

        List<Purchase> purchases = purchaseDAO.getByUserId(userId);
        System.out.println(purchases.size());
        List<PurchaseDTO> result = new ArrayList<>();

        for (Purchase purchase : purchases) {
            PurchaseDTO purchaseDTO = new PurchaseDTO();
            
            Factory factory = factoryDAO.GetById(purchase.factoryId);
            purchaseDTO.factoryName = factory != null ? factory.getName() : "Unknown";

            HashMap<String, Integer> chocolatesMap = new HashMap<>();
            for (Map.Entry<Integer, Integer> entry : purchase.chocolates.entrySet()) {
                Chocolate chocolate = chocolateDAO.getById(entry.getKey());
                String chocolateName = chocolate != null ? chocolate.getName() : "Unknown";
                chocolatesMap.put(chocolateName, entry.getValue());
            }
            purchaseDTO.chocolates = chocolatesMap;
            purchaseDTO.date = purchase.date;
            purchaseDTO.price = purchase.price;
            purchaseDTO.status = purchase.status;
            purchaseDTO.id = purchase.id;

            result.add(purchaseDTO);
        }

        return result;
    }
    
    
    @GET
    @Path("/getByFactoryId")
    @Produces(MediaType.APPLICATION_JSON)
    public List<PurchaseDTO> getPurchasesByFactoryId(@QueryParam("factoryId") int factoryId) {
        PurchaseDAO purchaseDAO = (PurchaseDAO) ctx.getAttribute("PurchaseDAO");
        ChocolateDAO chocolateDAO = (ChocolateDAO) ctx.getAttribute("ChocolateDAO");
        FactoryDAO factoryDAO = (FactoryDAO) ctx.getAttribute("FactoryDAO");

        List<Purchase> purchases = purchaseDAO.getByFactoryId(factoryId);
        List<PurchaseDTO> result = new ArrayList<>();

        for (Purchase purchase : purchases) {
            PurchaseDTO purchaseDTO = new PurchaseDTO();

            Factory factory = factoryDAO.GetById(purchase.getFactoryId());
            purchaseDTO.setFactoryName(factory != null ? factory.getName() : "Unknown");

            HashMap<String, Integer> chocolatesMap = new HashMap<>();
            for (Map.Entry<Integer, Integer> entry : purchase.getChocolates().entrySet()) {
                Chocolate chocolate = chocolateDAO.getById(entry.getKey());
                String chocolateName = chocolate != null ? chocolate.getName() : "Unknown";
                chocolatesMap.put(chocolateName, entry.getValue());
            }
            purchaseDTO.setChocolates(chocolatesMap);
            purchaseDTO.setDate(purchase.getDate());
            purchaseDTO.setPrice(purchase.getPrice());
            purchaseDTO.setStatus(purchase.getStatus());
            purchaseDTO.setId(purchase.getId());

            result.add(purchaseDTO);
        }

        return result;
    }

    
    @POST
    @Path("/cancelPurchase/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response cancelPurchase(@PathParam("id") int id) {
        PurchaseDAO purchaseDAO = (PurchaseDAO) ctx.getAttribute("PurchaseDAO");
        UserDAO userDAO = (UserDAO) ctx.getAttribute("UserDAO");
        Purchase p = purchaseDAO.getById(id);
        if(p==null) {
        	return Response.status(Response.Status.NOT_FOUND).entity("Purchase not found or could not be cancelled.").build();
        }
        User user = userDAO.getById(purchaseDAO.getById(id).userId);
        user.setPoints(user.getPoints()-(p.price/1000*133*4));
        userDAO.update(user);
        purchaseDAO.deleteById(id);
            return Response.status(Response.Status.OK).entity("Purchase cancelled successfully.").build();
    }

    @PUT
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public boolean save(Purchase obj) throws IOException {
        PurchaseDAO dao = (PurchaseDAO) ctx.getAttribute("PurchaseDAO");
        return dao.add(obj);
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public boolean delete(@PathParam("id") int id) {
        PurchaseDAO dao = (PurchaseDAO) ctx.getAttribute("PurchaseDAO");
        return dao.deleteById(id);
    }

    public PurchaseService() {
    }
    @POST
    @Path("/approvePurchase")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response approvePurchase(@QueryParam("purchaseId") int purchaseId) {
        PurchaseDAO purchaseDAO = (PurchaseDAO) ctx.getAttribute("PurchaseDAO");
        boolean success = purchaseDAO.updatePurchaseStatus(purchaseId, "Odobreno");
        if (success) {
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("Failed to approve purchase").build();
        }
    }

    @POST
    @Path("/rejectPurchase")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response rejectPurchase(@QueryParam("purchaseId") int purchaseId) {
        PurchaseDAO purchaseDAO = (PurchaseDAO) ctx.getAttribute("PurchaseDAO");
        boolean success = purchaseDAO.updatePurchaseStatus(purchaseId, "Odbijeno");
        if (success) {
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("Failed to reject purchase").build();
        }
    }

}
