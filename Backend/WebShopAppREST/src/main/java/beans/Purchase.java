package beans;

import java.time.LocalDate;
import java.util.List;

public class Purchase {
	public int id;
	public String uniqueId;
	public List<Chocolate> chocolates;
	public Factory factory;
	public int factoryId;
	public LocalDate date;
	public double price;
	public User user;
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
	public List<Chocolate> getChocolates() {
		return chocolates;
	}
	public void setChocolates(List<Chocolate> chocolates) {
		this.chocolates = chocolates;
	}
	public Factory getFactory() {
		return factory;
	}
	public void setFactory(Factory factory) {
		this.factory = factory;
	}
	public int getFactoryId() {
		return factoryId;
	}
	public void setFactoryId(int factoryId) {
		this.factoryId = factoryId;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
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
	public Purchase(int id, String uniqueId, List<Chocolate> chocolates, Factory factory, int factoryId, LocalDate date,
			double price, User user, String status) {
		super();
		this.id = id;
		this.uniqueId = uniqueId;
		this.chocolates = chocolates;
		this.factory = factory;
		this.factoryId = factoryId;
		this.date = date;
		this.price = price;
		this.user = user;
		this.status = status;
		this.userNameSurname = user.getName() + " " + user.getSurname();
	}
	
	
}
