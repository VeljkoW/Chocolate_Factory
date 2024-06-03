package beans;

public class Location {
	private int id;
	private double latitude; //sirina xd
	private double longitude;
	private int addressId;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public int getAddressId() {
		return addressId;
	}
	public void setAddressId(int addressId) {
		this.addressId = addressId;
	}
	public Location(int id, double latitude, double longitude, int addressId) {
		super();
		this.id = id;
		this.latitude = latitude;
		this.longitude = longitude;
		this.addressId = addressId;
	}
	public Location() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
}
