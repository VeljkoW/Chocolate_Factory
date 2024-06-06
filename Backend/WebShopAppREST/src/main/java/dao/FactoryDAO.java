package dao;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import beans.Chocolate;
import beans.Factory;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import Utilities.Reader;

public class FactoryDAO {
	private List<Factory> factories = new ArrayList<Factory>();
	private Gson gson = new Gson();
	private String contextpath;
	
	public FactoryDAO(String f) {
		///WebShopAppREST/src/main/resources/data/factories.json
		contextpath=f.split(".metadata")[0]+"WebShopAppREST\\src\\main\\resources\\data\\factories.json";
		factories=getAll();
	}
	public Factory GetById(int id)
	{
		return factories.stream().filter(t -> t.getId() == id).findFirst().orElse(null);
	}
	
	public List<Factory> getAll(){
		try {
		String json = Reader.readFileAsString(contextpath);
		Type listType = new TypeToken<List<Factory>>(){}.getType();
		List<Factory> retlist = gson.fromJson(json, listType);
		List<Factory> first = retlist.stream().filter(t->t.getStatus().equals("Open")).collect(Collectors.toList());
		List<Factory> other = retlist.stream().filter(t->!first.contains(t)).collect(Collectors.toList());
		List<Factory> sorted = new ArrayList<Factory>();
		sorted.addAll(first);
		sorted.addAll(other);
		return sorted;
		}
		catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	public boolean add(Factory factory) throws IOException {
		if (factory == null || !isValid(factory)) {
	        return false;
	    }
		factory.setId(factory.hashCode());
        factories.add(factory);
        
        if(factory.getGrade()<1 || factory.getLocationId()<0 || factory.getLogoImagePath().isEmpty() ||
        		factory.getName().isBlank() || factory.getStatus().isBlank() || factory.getClosingTime().isBlank() ||
        		factory.getOpeningTime().isBlank()) {
        	return false;
        }
        String json = gson.toJson(factories);
        
        try (FileWriter writer = new FileWriter(contextpath)) {
            writer.write(json);
        }
        catch(Exception e){
        	e.printStackTrace();
        	return false;
        }
        return true;
    }
    public Factory update(Factory updatedFactory){
        if (updatedFactory == null || !isValid(updatedFactory)) {
            return null;
        }
        
        for (int i = 0; i < factories.size(); i++) {
        	Factory factory = factories.get(i);
            if (factory.getId() == updatedFactory.getId()) {
            	factory.setName(updatedFactory.getName());
            	factory.setOpeningTime(updatedFactory.getOpeningTime());
            	factory.setClosingTime(updatedFactory.getClosingTime());
            	factory.setStatus(updatedFactory.getStatus());
            	factory.setLocationId(updatedFactory.getLocationId());
            	factory.setLogoImagePath(updatedFactory.getLogoImagePath());
            	factory.setGrade(updatedFactory.getGrade());
            	factories.set(i, factory);
                if(write())
                	return factory;
            }
        }
        return null;
    }
    private boolean isValid(Factory factory) {
        if (factory.getName() == null || factory.getOpeningTime() == null ||
            factory.getClosingTime() == null || factory.getStatus() == null ||
            factory.getLocationId() <= 0 || factory.getLogoImagePath() == null ||
            factory.getGrade() <= 0 || factory.getGrade() < 1) {
            return false;
        }
        return true;
    }
    public boolean delete(int id) {
    	Factory f = factories.stream().filter(t->t.getId()==id).findFirst().orElse(null);
    	if(f==null) {
    		return false;
    	}
    	factories.remove(f);
    	
    	if(write()) {
    		return true;
    	}
    	return false;
    }
    
    
    
    public boolean write() {
        String json = gson.toJson(factories);
    	try (FileWriter writer = new FileWriter(contextpath)) {
            writer.write(json);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    	return true;
    }
}
