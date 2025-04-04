package dao;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import Utilities.CommentNewDTO;
import Utilities.Reader;
import beans.Chocolate;
import beans.Comment;

public class CommentDAO {
	private List<Comment> comments = new ArrayList<Comment>();
	private Gson gson = new Gson();
	
	private String contextpath;
	
	public CommentDAO(String c) {
		///WebShopAppREST/src/main/resources/data/comments.json
		contextpath=c.split(".metadata")[0]+"WebShopAppREST\\src\\main\\resources\\data\\comments.json";
		comments=getAll();
	}
	public List<Comment> getAllByFactoryId(int id)
	{
		return comments.stream().filter(c -> c.getFactoryId() == id).collect(Collectors.toList());
	}
	public List<Comment> getAll(){
		System.out.println(contextpath);
		try {
			
			String json = Reader.readFileAsString(contextpath);
		//System.out.println(json);
		Type listType = new TypeToken<List<Comment>>(){}.getType();
		List<Comment> retlist = gson.fromJson(json, listType);
		return retlist;
		}
		catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
    public boolean addFromDTO(CommentNewDTO newcomment) throws IOException {
    	Comment c = new Comment();
    	c.setComment(newcomment.comment);
    	c.setFactoryId(newcomment.factoryId);
    	c.setId(c.hashCode());
    	c.setGrade(newcomment.grade);
    	c.setStatus("Zahtev");
    	c.setUserId(newcomment.userId);
    	comments.add(c);
        String json = gson.toJson(comments);
        
        try (FileWriter writer = new FileWriter(contextpath)) {
            writer.write(json);
        }
        catch(Exception e){
        	e.printStackTrace();
        	return false;
        }
        return true;
    }
    public boolean add(Comment comment) throws IOException {
    	comment.setId(comment.hashCode());
    	comments.add(comment);
        String json = gson.toJson(comments);
        
        try (FileWriter writer = new FileWriter(contextpath)) {
            writer.write(json);
        }
        catch(Exception e){
        	e.printStackTrace();
        	return false;
        }
        return true;
    }
    public Comment update(Comment updatedComment){
        for (int i = 0; i < comments.size(); i++) {
        	Comment comment = comments.get(i);
            if (comment.getId() == updatedComment.getId()) {
            	comment.setUserId(updatedComment.getUserId());
            	comment.setFactoryId(updatedComment.getFactoryId());
            	comment.setComment(updatedComment.getComment());
            	comment.setGrade(updatedComment.getGrade());
            	comments.set(i, comment);
                if(write())
                	return comment;
            }
        }
        return null;
    }
    public boolean delete(int id) {
    	Comment c = comments.stream().filter(t->t.getId()==id).findFirst().orElse(null);
    	if(c==null) {
    		return false;
    	}
    	comments.remove(c);
    	

    	if(write()) {
    		return true;
    	}
    	return false;
    }
    public boolean approveComment(int id) {
        Comment c = comments.stream()
                            .filter(t -> t.getId() == id)
                            .findFirst()
                            .orElse(null);
        if (c == null) {
            return false;
        }
        c.setStatus("Odobren");
        return write();
    }

    public boolean rejectComment(int id) {
        Comment c = comments.stream()
                            .filter(t -> t.getId() == id)
                            .findFirst()
                            .orElse(null);
        if (c == null) {
            return false;
        }
        c.setStatus("Odbijen");
        return write();
    }
    
    public boolean write() {
        String json = gson.toJson(comments);
    	try (FileWriter writer = new FileWriter(contextpath)) {
            writer.write(json);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    	return true;
    }
}
