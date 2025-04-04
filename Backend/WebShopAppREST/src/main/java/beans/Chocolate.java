package beans;

import java.util.Objects;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Chocolate {
	private int id;
	private String name;
	private double price;
	private String variety;
	private int factoryId;
	private String type;
	private double mass;
	private String description;
	private String imagePath;	//this needs to change aaaaaaaaaaaaaaaa
	private String status;
	private int stock;
	private boolean IsDeleted;
	
	public boolean getIsDeleted() {
		return IsDeleted;
	}
	public void setIsDeleted(boolean isDeleted) {
		IsDeleted = isDeleted;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getVariety() {
		return variety;
	}
	public void setVariety(String variety) {
		this.variety = variety;
	}
	public int getFactoryId() {
		return factoryId;
	}
	public void setFactoryId(int factoryId) {
		this.factoryId = factoryId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public double getMass() {
		return mass;
	}
	public void setMass(double mass) {
		this.mass = mass;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	public Chocolate(int id, String name, double price, String variety, int factoryId, String type, double mass,
			String description, String imagePath, String status, int stock,boolean isDeleted) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.variety = variety;
		this.factoryId = factoryId;
		this.type = type;
		this.mass = mass;
		this.description = description;
		this.imagePath = imagePath;
		this.status = status;
		this.stock = stock;
		this.IsDeleted=isDeleted;
	}
	public Chocolate() {
		super();
	}
	@Override
	public int hashCode() {
		return Objects.hash(IsDeleted, description, factoryId, id, imagePath, mass, name, price, status, type,
				variety);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Chocolate other = (Chocolate) obj;
		return IsDeleted == other.IsDeleted && Objects.equals(description, other.description)
				&& factoryId == other.factoryId && id == other.id && Objects.equals(imagePath, other.imagePath)
				&& Double.doubleToLongBits(mass) == Double.doubleToLongBits(other.mass)
				&& Objects.equals(name, other.name)
				&& Double.doubleToLongBits(price) == Double.doubleToLongBits(other.price)
				&& Objects.equals(status, other.status) && Objects.equals(type, other.type)
				&& Objects.equals(variety, other.variety);
	}
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("{\n");
	    sb.append("  \"description\": \"").append(description).append("\",\n");
	    sb.append("  \"factoryId\": ").append(factoryId).append(",\n");
	    sb.append("  \"id\": ").append(id).append(",\n");
	    sb.append("  \"imagePath\": \"").append(imagePath).append("\",\n");
	    sb.append("  \"IsDeleted\": ").append(IsDeleted).append(",\n");
	    sb.append("  \"mass\": ").append(mass).append(",\n");
	    sb.append("  \"name\": \"").append(name).append("\",\n");
	    sb.append("  \"price\": ").append(price).append(",\n");
	    sb.append("  \"status\": \"").append(status).append("\",\n");
	    sb.append("  \"stock\": ").append(stock).append(",\n");
	    sb.append("  \"type\": \"").append(type).append("\",\n");
	    sb.append("  \"variety\": \"").append(variety).append("\"\n");
	    sb.append("}");
	    return sb.toString();
	}

}
