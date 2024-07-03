package beans;

import java.util.List;

public class Cart {
	private int id;
	private List<Integer> chocolates;
	private int userId;
	private double price;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public List<Integer> getChocolates() {
		return chocolates;
	}
	public void setChocolates(List<Integer> chocolates) {
		this.chocolates = chocolates;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
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
	public Cart(int id, List<Integer> chocolates, double price,int userId) {
		super();
		this.id = id;
		this.chocolates = chocolates;
		this.price = price;
		this.userId = userId;
	}
	
}
