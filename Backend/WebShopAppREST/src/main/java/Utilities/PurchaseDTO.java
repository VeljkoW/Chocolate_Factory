package Utilities;

import java.util.HashMap;

public class PurchaseDTO {
	public Integer id;
	public String factoryName;
	public HashMap<String,Integer> chocolates;
	public String date;
	public double price;
	public String status;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getFactoryName() {
		return factoryName;
	}
	public void setFactoryName(String factoryName) {
		this.factoryName = factoryName;
	}
	public HashMap<String, Integer> getChocolates() {
		return chocolates;
	}
	public void setChocolates(HashMap<String, Integer> chocolates) {
		this.chocolates = chocolates;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
