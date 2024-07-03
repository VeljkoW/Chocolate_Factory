package dao;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import Utilities.Reader;
import beans.UserType;

public class UserTypeDAO {
	private List<UserType> usertypes = new ArrayList<UserType>();
	private Gson gson = new Gson();
	
	private String contextpath;
	
	public UserTypeDAO(String a) {
		contextpath=a.split(".metadata")[0]+"WebShopAppREST\\src\\main\\resources\\data\\usertypes.json";
		usertypes=getAll();
	}
	public UserType getById(int id)
	{
		if(usertypes == null)
		{
			return null;
		}
		return usertypes.stream().filter(t -> t.getId() == id).findFirst().orElse(null);
	}
	public List<UserType> getAll(){
		try {
		String json = Reader.readFileAsString(contextpath);
		Type listType = new TypeToken<List<UserType>>(){}.getType();
		List<UserType> jsondata = gson.fromJson(json, listType);
		List<UserType> retlist = jsondata.stream().collect(Collectors.toList());
		return retlist;
		}
		catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
