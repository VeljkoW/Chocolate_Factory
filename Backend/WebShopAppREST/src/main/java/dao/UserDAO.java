package dao;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import Utilities.Reader;
import beans.Chocolate;
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
		if(users == null)
		{
			return null;
		}
		return users.stream().filter(t -> t.getId() == id).findFirst().orElse(null);
	}
	public User getByUserName(String Username)
	{
		if(users == null)
		{
			return null;
		}
		return users.stream().filter(t -> t.getUsername().equals(Username)).findFirst().orElse(null);
	}
	public boolean updateManager(int userId,int factoryId)
	{
		User user = getById(userId);
		if(user != null)
		{
			if(user.getFactoryId() == -1 || user.getFactoryId() != factoryId)
			{
				user.setFactoryId(factoryId);
			}
			if(updateFactoryId(user) != null)
				return true;
		}
		return false;
	}
	public User updateFactoryId(User updatedUser)
	{
		 for (int i = 0; i < users.size(); i++) {
	            User user = users.get(i);
	            if (user.getId() == updatedUser.getId()) {
	            	user.setUsername(updatedUser.getUsername());
	            	user.setName(updatedUser.getName());
	            	user.setSurname(updatedUser.getSurname());
	            	user.setGender(updatedUser.getGender());
	            	user.setDateOfBirth(updatedUser.getDateOfBirth());
	            	user.setUloga(updatedUser.getUloga());
	            	user.setFactoryId(updatedUser.getFactoryId());
	            	user.setPoints(updatedUser.getPoints());
	            	user.setUserTypeId(updatedUser.getUserTypeId());
	            	user.setBlocked(updatedUser.getBlocked());
	            	users.set(i, user);
	                if(write())
	                	return user;
	            }
	        }
	        return null;
	}
	public User update(User updatedUser)
	{
		 for (int i = 0; i < users.size(); i++) {
	            User user = users.get(i);
	            if (user.getId() == updatedUser.getId()) {
	            	user.setUsername(updatedUser.getUsername());
	            	user.setName(updatedUser.getName());
	            	user.setSurname(updatedUser.getSurname());
	            	user.setGender(updatedUser.getGender());
	            	user.setDateOfBirth(updatedUser.getDateOfBirth());
	            	user.setPoints(updatedUser.getPoints());
	            	users.set(i, user);
	                if(write())
	                	return user;
	            }
	        }
	        return null;
	}

	public User updatePassword(User updatedUser)
	{
		 for (int i = 0; i < users.size(); i++) {
	            User user = users.get(i);
	            if (user.getId() == updatedUser.getId()) {
	            	user.setPassword(updatedUser.getPassword());
	            	users.set(i, user);
	                if(write())
	                	return user;
	            }
	        }
	        return null;
	}
	public User blockUser(User updatedUser)
	{
		for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            if (user.getId() == updatedUser.getId()) {
            	user.setBlocked(true);
            	users.set(i, user);
                if(write())
                	return user;
            }
        }
        return null;
	}
	public User deleteUser(User updatedUser)
	{
		for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            if (user.getId() == updatedUser.getId()) {
            	user.setDeleted(true);
            	users.set(i, user);
                if(write())
                	return user;
            }
        }
        return null;
	}
	public User unblockUser(User updatedUser)
	{
		for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            if (user.getId() == updatedUser.getId()) {
            	user.setBlocked(false);
            	users.set(i, user);
                if(write())
                	return user;
            }
        }
        return null;
	}
	public boolean authenticatePassword(String username, String password)
	{
		User user = getByUserName(username);
		if(user.getPassword().equals(password))
		{
			return true;
		}
		return false;
	}
	
	public List<User> getAll(){
		try {
		//System.out.println(contextpath);
		String json = Reader.readFileAsString(contextpath);
		//System.out.println(json);
		Type listType = new TypeToken<List<User>>(){}.getType();
		List<User> jsondata = gson.fromJson(json, listType);
		List<User> retlist = jsondata.stream().filter(t -> t.getDeleted() != true).collect(Collectors.toList()); //put it that it doesnt read deleted users
		//System.out.println(retlist.size());
		//System.out.println(retlist);
		return retlist;
		}
		catch(Exception e) {
			//System.out.println("catch");
			e.printStackTrace();
			return null;
		}
	}
	public List<User> getAllByRole(String role)
	{
		if(role.equals("Administrator"))
		{
			return users.stream().filter(t -> t.getUloga().equals("Administrator")).collect(Collectors.toList());
		}
		if(role.equals("Customer"))
		{
			return users.stream().filter(t -> t.getUloga().equals("Customer")).collect(Collectors.toList());
		}
		if(role.equals("Manager"))
		{
			return users.stream().filter(t -> t.getUloga().equals("Manager")).collect(Collectors.toList());
		}
		return users.stream().filter(t -> t.getUloga().equals("Employee")).collect(Collectors.toList());
	}
	public List<User> getFreeManagers(){
		return users.stream().filter(t -> t.getUloga().equals("Manager") && t.getFactoryId() == -1 && !t.getBlocked()).collect(Collectors.toList());
	}
	
	
    public int add(User user) throws IOException {
    	user.setId(user.hashCode());
    	if(getByUserName(user.getUsername()) != null)
    	{
    		return -1;
    	}
    	users.add(user);
        String json = gson.toJson(users);
        
        try (FileWriter writer = new FileWriter(contextpath)) {
            writer.write(json);
        }
        catch(Exception e){
        	e.printStackTrace();
        	return -1;
        }
        return user.getId();
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
