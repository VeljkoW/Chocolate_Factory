package beans;

import java.time.LocalDate;
import java.util.List;

public class Purchase {
	public int id;
	public String uniqueId;
	public List<Integer> chocolates;
	public int factoryId;
	public String date;
	public double price;
	public int userId;
	public String userNameSurname;
	public String status;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUniqueId() {
		return uniqueId;
	}
	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}
	public List<Integer> getChocolates() {
		return chocolates;
	}
	public void setChocolates(List<Integer> chocolates) {
		this.chocolates = chocolates;
	}
	public int getFactoryId() {
		return factoryId;
	}
	public void setFactoryId(int factoryId) {
		this.factoryId = factoryId;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserNameSurname() {
		return userNameSurname;
	}
	public void setUserNameSurname(String userNameSurname) {
		this.userNameSurname = userNameSurname;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Purchase() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Purchase(int id, String uniqueId, List<Integer> chocolates, int factoryId, String date,
			double price, int userId, String status) {
		super();
		this.id = id;
		this.uniqueId = uniqueId;
		this.chocolates = chocolates;
		this.factoryId = factoryId;
		this.date = date;
		this.price = price;
		this.userId = userId;
		this.status = status;
		this.userNameSurname = "";
	}
	
	
}
