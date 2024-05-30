package beans;

import java.util.List;

public class Factory {
	private int id;
	private String name;
	private List<Chocolate> chocolates;
	private String openingTime;
	private String closingTime;
	private String status;
	private int locationId;
	private Location location;
	private String logoImagePath; //needs to change
	private double grade;
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
	public List<Chocolate> getChocolates() {
		return chocolates;
	}
	public void setChocolates(List<Chocolate> chocolates) {
		this.chocolates = chocolates;
	}
	public String getOpeningTime() {
		return openingTime;
	}
	public void setOpeningTime(String openingTime) {
		this.openingTime = openingTime;
	}
	public String getClosingTime() {
		return closingTime;
	}
	public void setClosingTime(String closingTime) {
		this.closingTime = closingTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getLocationId() {
		return locationId;
	}
	public void setLocationId(int locationId) {
		this.locationId = locationId;
	}
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	public String getLogoImagePath() {
		return logoImagePath;
	}
	public void setLogoImagePath(String logoImagePath) {
		this.logoImagePath = logoImagePath;
	}
	public double getGrade() {
		return grade;
	}
	public void setGrade(double grade) {
		this.grade = grade;
	}
	public Factory(int id, String name, List<Chocolate> chocolates, String openingTime, String closingTime,
			String status, Location location, String logoImagePath, double grade) {
		super();
		this.id = id;
		this.name = name;
		this.chocolates = chocolates;
		this.openingTime = openingTime;
		this.closingTime = closingTime;
		this.status = status;
		this.location = location;
		this.locationId = location.getId();
		this.logoImagePath = logoImagePath;
		this.grade = grade;
	}
	public Factory() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}
