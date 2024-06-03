package dao;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import Utilities.Reader;
import beans.Location;

public class LocationDAO {
	private List<Location> locations = new ArrayList<Location>();
	private Gson gson = new Gson();
	
	private String contextpath;
	
	public LocationDAO(String c) {
		///WebShopAppREST/src/main/resources/data/locations.json
		contextpath=c.split(".metadata")[0]+"WebShopAppREST\\src\\main\\resources\\data\\locations.json";
		locations=getAll();
	}

	public List<Location> getAll(){
		System.out.println(contextpath);
		try {
			
			String json = Reader.readFileAsString(contextpath);
		System.out.println(json);
		Type listType = new TypeToken<List<Location>>(){}.getType();
		List<Location> retlist = gson.fromJson(json, listType);
		return retlist;
		}
		catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
    public boolean add(Location location) throws IOException {
    	location.setId(location.hashCode());
    	locations.add(location);
        String json = gson.toJson(locations);
        
        try (FileWriter writer = new FileWriter(contextpath)) {
            writer.write(json);
        }
        catch(Exception e){
        	e.printStackTrace();
        	return false;
        }
        return true;
    }

    public boolean delete(int id) {
    	Location l = locations.stream().filter(t->t.getId()==id).findFirst().orElse(null);
    	if(l==null) {
    		return false;
    	}
    	locations.remove(l);
    	

    	if(write()) {
    		return true;
    	}
    	return false;
    }
    public boolean write() {
        String json = gson.toJson(locations);
    	try (FileWriter writer = new FileWriter(contextpath)) {
            writer.write(json);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    	return true;
    }
}
