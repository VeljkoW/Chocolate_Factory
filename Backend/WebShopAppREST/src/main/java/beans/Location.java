package beans;

public class Location {
	private int id;
	private double latitude; //sirina xd
	private double longitude;
	private int addressId;
	private Address address;
	
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
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public Location(int id, double latitude, double longitude, Address address) {
		super();
		this.id = id;
		this.latitude = latitude;
		this.longitude = longitude;
		this.address = address;
		this.addressId = address.getId();
	}
	public Location() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
}
