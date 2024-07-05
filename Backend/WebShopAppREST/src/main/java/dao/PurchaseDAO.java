package dao;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import Utilities.Reader;
import beans.Purchase;

public class PurchaseDAO {
	private List<Purchase> purchases = new ArrayList<Purchase>();
	private Gson gson = new Gson();
	
	private String contextpath;
	
	public PurchaseDAO(String a) {
		contextpath=a.split(".metadata")[0]+"WebShopAppREST\\src\\main\\resources\\data\\purchases.json";
		purchases=getAll();
		gson = new GsonBuilder().registerTypeAdapter(new TypeToken<HashMap<Integer, Integer>>(){}.getType(), new HashMapAdapter()).create();
	}
	public Purchase getById(int id)
	{
		if(purchases == null)
		{
			return null;
		}
		return purchases.stream().filter(t -> t.getId() == id).findFirst().orElse(null);
	}
	public List<Purchase> getAll(){
		try {
		System.out.println(contextpath);
		String json = Reader.readFileAsString(contextpath);
		//System.out.println(json);
		Type listType = new TypeToken<List<Purchase>>(){}.getType();
		List<Purchase> jsondata = gson.fromJson(json, listType);
		List<Purchase> retlist = jsondata.stream().collect(Collectors.toList());
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
    public boolean add(Purchase purchase) throws IOException {
    	purchase.setId(purchase.hashCode());
    	purchases.add(purchase);
        String json = gson.toJson(purchases);
        
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
    	Purchase c = purchases.stream().filter(t->t.getId()==id).findFirst().orElse(null);
    	if(c==null) {
    		return false;
    	}
    	purchases.remove(c);
    	

    	if(write()) {
    		return true;
    	}
    	return false;
    }
    public boolean write() {
        String json = gson.toJson(purchases);
    	try (FileWriter writer = new FileWriter(contextpath)) {
            writer.write(json);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    	return true;
    }
    private static class HashMapAdapter implements com.google.gson.JsonSerializer<HashMap<Integer, Integer>> {
        @Override
        public JsonElement serialize(HashMap<Integer, Integer> map, Type type,
                com.google.gson.JsonSerializationContext context) {
            JsonObject jsonObject = new JsonObject();
            for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
                jsonObject.addProperty(entry.getKey().toString(), entry.getValue());
            }
            return jsonObject;
        }
    }
}
