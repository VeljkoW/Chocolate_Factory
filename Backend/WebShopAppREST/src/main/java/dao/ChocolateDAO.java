package dao;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import Utilities.Reader;
import beans.Chocolate;

public class ChocolateDAO {
	private List<Chocolate> chocolates = new ArrayList<Chocolate>();
	private Gson gson = new Gson();
	
	private String contextpath;
	
	public ChocolateDAO(String c) {
		contextpath=c;
		chocolates=getAll();
	}

	public List<Chocolate> getAll(){
		System.out.println(contextpath);
		
		try {
			
			String json = Reader.readFileAsString(contextpath + "/WEB-INF/classes/data/chocolates.json");
		System.out.println(json);
		Type listType = new TypeToken<List<Chocolate>>(){}.getType();
		List<Chocolate> retlist = gson.fromJson(json, listType);
		return retlist;
		}
		catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
    public boolean add(Chocolate chocolate) throws IOException {
    	chocolate.setId(chocolate.hashCode());
        chocolates.add(chocolate);
        String json = gson.toJson(chocolates);
        
        try (FileWriter writer = new FileWriter(contextpath + "/WEB-INF/classes/data/chocolates.json")) {
            writer.write(json);
        }
        catch(Exception e){
        	e.printStackTrace();
        	return false;
        }
        return true;
    }
    public Chocolate update(Chocolate updatedChocolate){
    	System.out.println(updatedChocolate.getId());
    	System.out.println(chocolates.size());
        for (int i = 0; i < chocolates.size(); i++) {
            Chocolate chocolate = chocolates.get(i);
            if (chocolate.getId() == updatedChocolate.getId()) {
                chocolate.setName(updatedChocolate.getName());
                chocolate.setPrice(updatedChocolate.getPrice());
                chocolate.setVariety(updatedChocolate.getVariety());
                chocolate.setFactoryId(updatedChocolate.getFactoryId());
                chocolate.setType(updatedChocolate.getType());
                chocolate.setMass(updatedChocolate.getMass());
                chocolate.setDescription(updatedChocolate.getDescription());
                chocolate.setImagePath(updatedChocolate.getImagePath());
                chocolate.setStatus(updatedChocolate.getStatus());
                chocolate.setStock(updatedChocolate.getStock());
                chocolates.set(i, chocolate);
                if(write())
                	return chocolate;
            }
        }
        return null;
    }
    public boolean delete(int id) {
    	Chocolate c = chocolates.stream().filter(t->t.getId()==id).findFirst().orElse(null);
    	if(c==null) {
    		return false;
    	}
    	chocolates.remove(c);
    	

    	if(write()) {
    		return true;
    	}
    	return false;
    }
    
    
    
    public boolean write() {
        String json = gson.toJson(chocolates);
    	try (FileWriter writer = new FileWriter(contextpath + "/WEB-INF/classes/data/chocolates.json")) {
            writer.write(json);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    	return true;
    }
}