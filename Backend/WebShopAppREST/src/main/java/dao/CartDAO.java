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
import beans.Cart;

public class CartDAO {
	private List<Cart> carts = new ArrayList<Cart>();
	private Gson gson = new Gson();
	
	private String contextpath;
	
	public CartDAO(String a) {
		contextpath=a.split(".metadata")[0]+"WebShopAppREST\\src\\main\\resources\\data\\carts.json";
		carts=getAll();
	}
	public Cart getById(int id)
	{
		if(carts == null)
		{
			return null;
		}
		return carts.stream().filter(t -> t.getId() == id).findFirst().orElse(null);
	}
	public Cart getByUserId(int userId)
	{
		if(carts == null)
		{
			return null;
		}
		return carts.stream().filter(t -> t.getUserId() == userId).findFirst().orElse(null);
	}
	public List<Cart> getAll(){
		try {
		//System.out.println(contextpath);
		String json = Reader.readFileAsString(contextpath);
		//System.out.println(json);
		Type listType = new TypeToken<List<Cart>>(){}.getType();
		List<Cart> jsondata = gson.fromJson(json, listType);
		List<Cart> retlist = jsondata.stream().collect(Collectors.toList());
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
    public boolean add(Cart cart) throws IOException {
    	cart.setId(cart.hashCode());
    	carts.add(cart);
        String json = gson.toJson(carts);
        
        try (FileWriter writer = new FileWriter(contextpath)) {
            writer.write(json);
        }
        catch(Exception e){
        	e.printStackTrace();
        	return false;
        }
        return true;
    }
    public boolean deleteById(int id) {
    	Cart c = carts.stream().filter(t->t.getId()==id).findFirst().orElse(null);
    	if(c==null) {
    		return false;
    	}
    	carts.remove(c);
    	

    	if(write()) {
    		return true;
    	}
    	return false;
    }
    public boolean write() {
        String json = gson.toJson(carts);
    	try (FileWriter writer = new FileWriter(contextpath)) {
            writer.write(json);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    	return true;
    }
}
