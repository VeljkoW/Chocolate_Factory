package beans;

public class Address {
	private int id;
	private String street;
	private String number;
	private String place;
	private String postalCode;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	public Address(int id, String street, String number, String place, String postalCode) {
		super();
		this.id = id;
		this.street = street;
		this.number = number;
		this.place = place;
		this.postalCode = postalCode;
	}
	public Address() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}
