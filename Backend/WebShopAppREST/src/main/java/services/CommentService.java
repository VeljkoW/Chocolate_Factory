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

import beans.Chocolate;
import beans.Comment;
import dao.ChocolateDAO;
import dao.CommentDAO;


@Path("/comment")
public class CommentService {
	@Context
	ServletContext ctx;
	
	@PostConstruct
	public void init() {
		if (ctx.getAttribute("CommentDAO") == null) {
	    	String contextPath = ctx.getRealPath("");
			ctx.setAttribute("CommentDAO", new CommentDAO(contextPath));
		}
	}
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Comment> getAll() throws IOException{
		CommentDAO dao =(CommentDAO) ctx.getAttribute("CommentDAO");
		return dao.getAll();
	}
	@GET
	@Path("/getByFactoryId")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Comment> getAllByFactoryId(@QueryParam("factoryId") int factoryId) {
		CommentDAO dao =(CommentDAO) ctx.getAttribute("CommentDAO");
		return dao.getAllByFactoryId(factoryId);
	}
	@PUT
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public boolean save(Comment obj) throws IOException {
		CommentDAO dao =(CommentDAO) ctx.getAttribute("CommentDAO");
		return dao.add(obj);
	}
    @PATCH
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Comment update(Comment updatedComment) throws IOException {
    	CommentDAO dao = (CommentDAO) ctx.getAttribute("CommentDAO");
        return dao.update(updatedComment);
    }
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public boolean delete(@PathParam("id") int id) {
    	System.out.println(id);
    	CommentDAO dao = (CommentDAO) ctx.getAttribute("CommentDAO");
    	return dao.delete(id);
    }
    
	public CommentService() {
	}
}
