package beans;

import java.util.List;

public class Cart {
	private int id;
	private List<Chocolate> chocolates;
	private int userId;
	private User user;
	private double price;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public List<Chocolate> getChocolates() {
		return chocolates;
	}
	public void setChocolates(List<Chocolate> chocolates) {
		this.chocolates = chocolates;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public Cart() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Cart(int id, List<Chocolate> chocolates, User user, double price) {
		super();
		this.id = id;
		this.chocolates = chocolates;
		this.user = user;
		this.price = price;
		this.userId = user.getId();
	}
	
}
