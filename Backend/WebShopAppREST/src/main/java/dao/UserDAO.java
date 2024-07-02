package dao;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import Utilities.Reader;
import beans.User;

public class UserDAO {

	private List<User> users = new ArrayList<User>();
	private Gson gson = new Gson();
	
	private String contextpath;
	
	public UserDAO(String a) {
		contextpath=a.split(".metadata")[0]+"WebShopAppREST\\src\\main\\resources\\data\\users.json";
		users=getAll();
	}
	public User getById(int id)
	{
		return users.stream().filter(t -> t.getId() == id).findFirst().orElse(null);
	}

	public List<User> getAll(){
		try {
			
		String json = Reader.readFileAsString(contextpath);
		Type listType = new TypeToken<List<User>>(){}.getType();
		List<User> retlist = gson.fromJson(json, listType);
		return retlist;
		}
		catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
    public boolean add(User user) throws IOException {
    	user.setId(user.hashCode());
    	users.add(user);
        String json = gson.toJson(users);
        
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
    	User u = users.stream().filter(t->t.getId()==id).findFirst().orElse(null);
    	if(u==null) {
    		return false;
    	}
    	users.remove(u);
    	

    	if(write()) {
    		return true;
    	}
    	return false;
    }
    public boolean write() {
        String json = gson.toJson(users);
    	try (FileWriter writer = new FileWriter(contextpath)) {
            writer.write(json);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    	return true;
    }
}
