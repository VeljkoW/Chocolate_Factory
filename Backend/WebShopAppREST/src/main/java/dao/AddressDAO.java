package dao;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import Utilities.Reader;
import beans.Address;

public class AddressDAO {
	private List<Address> addresses = new ArrayList<Address>();
	private Gson gson = new Gson();
	
	private String contextpath;
	
	public AddressDAO(String a) {
		contextpath=a.split(".metadata")[0]+"WebShopAppREST\\src\\main\\resources\\data\\addresses.json";
		addresses=getAll();
	}
	public Address getById(int id)
	{
		return addresses.stream().filter(t -> t.getId() == id).findFirst().orElse(null);
	}

	public List<Address> getAll(){
		try {
			
			String json = Reader.readFileAsString(contextpath);
		Type listType = new TypeToken<List<Address>>(){}.getType();
		List<Address> retlist = gson.fromJson(json, listType);
		return retlist;
		}
		catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
    public boolean add(Address address) throws IOException {
    	address.setId(address.hashCode());
    	addresses.add(address);
        String json = gson.toJson(addresses);
        
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
    	Address a = addresses.stream().filter(t->t.getId()==id).findFirst().orElse(null);
    	if(a==null) {
    		return false;
    	}
    	addresses.remove(a);
    	

    	if(write()) {
    		return true;
    	}
    	return false;
    }
    public boolean write() {
        String json = gson.toJson(addresses);
    	try (FileWriter writer = new FileWriter(contextpath)) {
            writer.write(json);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    	return true;
    }
}
