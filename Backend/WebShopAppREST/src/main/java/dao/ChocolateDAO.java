package dao;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import Utilities.Reader;
import beans.Address;
import beans.Chocolate;

public class ChocolateDAO {
	private List<Chocolate> chocolates = new ArrayList<Chocolate>();
	private Gson gson = new Gson();
	
	private String contextpath;
	
	public ChocolateDAO(String c) {
		///WebShopAppREST/src/main/resources/data/chocolates.json
		contextpath=c.split(".metadata")[0]+"WebShopAppREST\\src\\main\\resources\\data\\chocolates.json";
		chocolates=getAll();
	}
	public List<Chocolate> getAllByFactoryId(int id)
	{
		getAll();
		return chocolates.stream().filter(c -> c.getFactoryId() == id).filter(t->t.getIsDeleted()==false).collect(Collectors.toList());
	}
	public Chocolate getById(int id)
	{
		getAll();
		return chocolates.stream().filter(t -> t.getId() == id).findFirst().orElse(null);
	}

	public List<Chocolate> getAll(){
		try {
			String json = Reader.readFileAsString(contextpath);
			Type listType = new TypeToken<List<Chocolate>>(){}.getType();
			List<Chocolate> jsondata = gson.fromJson(json, listType);
			List<Chocolate> retlist=jsondata.stream().filter(t->t.getIsDeleted()==false).collect(Collectors.toList());
			return retlist;
			}
		catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
    public boolean add(Chocolate chocolate) throws IOException {

    	if(chocolate.getId()<0 || chocolate.getImagePath()==null || chocolate.getName() == null ||
    	   chocolate.getPrice()<0 || chocolate.getStatus()==null || chocolate.getStock()<0 || 
    	   chocolate.getVariety()==null || chocolate.getDescription()==null || chocolate.getVariety()==null ||
    	   chocolate.getFactoryId()<0 || chocolate.getMass()<0 || chocolate.getType().isEmpty()) 
    	{
    		return false;    		
    	}
    	chocolate.setId(chocolate.hashCode());
        chocolates.add(chocolate);
        String json = gson.toJson(chocolates);
        
        try (FileWriter writer = new FileWriter(contextpath)) {
            writer.write(json);
        }
        catch(Exception e){
        	e.printStackTrace();
        	return false;
        }
        return true;
    }
    public Chocolate update(Chocolate updatedChocolate){
    	if(updatedChocolate.getId()<1 || updatedChocolate.getImagePath()==null || updatedChocolate.getName() == null ||
			updatedChocolate.getPrice()<1 || updatedChocolate.getStatus()==null || updatedChocolate.getStock()<0 || 
			updatedChocolate.getVariety()==null || updatedChocolate.getDescription()==null || updatedChocolate.getVariety()==null ||
			updatedChocolate.getFactoryId()<0 || updatedChocolate.getMass()<0 || updatedChocolate.getType().isEmpty()) 
	    	{	
	    		return null;    		
	    	}
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
                chocolate.setIsDeleted(updatedChocolate.getIsDeleted());
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
    	c.setIsDeleted(true);
    	update(c);
    	if(write()) {
    		return true;
    	}
    	return false;
    }
    
    
    
    public boolean write() {
        String json = gson.toJson(chocolates);
    	try (FileWriter writer = new FileWriter(contextpath)) {
            writer.write(json);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    	return true;
    }
}