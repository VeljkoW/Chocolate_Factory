package beans;

import java.time.LocalDate;
import java.util.List;

public class User {
	private int id;
	private String username;
	private String password;
	private String name;
	private String surname;
	private String gender;
	private LocalDate dateOfBirth;
	private String uloga;
	private List<Purchase> purchases;
	private Cart cart;
	private int cartId;
	private Factory factory;
	private int factoryId;
	private int points;
	private UserType userType;
	private int userTypeId;
	
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public User(int id, String username, String password, String name, String surname, String gender,
			LocalDate dateOfBirth, String uloga, List<Purchase> purchases, Cart cart, Factory factory, int points,
			UserType userType) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.name = name;
		this.surname = surname;
		this.gender = gender;
		this.dateOfBirth = dateOfBirth;
		this.uloga = uloga;
		this.purchases = purchases;
		this.cart = cart;
		this.factory = factory;
		this.points = points;
		this.userType = userType;
		this.factoryId = factory.getId();	//needs to change probably
		this.cartId = cart.getId();			
		this.userTypeId = cart.getId();
	}


	public List<Purchase> getPurchases() {
		return purchases;
	}
	public void setPurchases(List<Purchase> purchases) {
		this.purchases = purchases;
	}
	public Cart getCart() {
		return cart;
	}
	public void setCart(Cart cart) {
		this.cart = cart;
	}
	public int getCartId() {
		return cartId;
	}
	public void setCartId(int cartId) {
		this.cartId = cartId;
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
	public int getPoints() {
		return points;
	}
	public void setPoints(int points) {
		this.points = points;
	}
	public UserType getUserType() {
		return userType;
	}
	public void setUserType(UserType userType) {
		this.userType = userType;
	}
	public int getUserTypeId() {
		return userTypeId;
	}
	public void setUserTypeId(int userTypeId) {
		this.userTypeId = userTypeId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getUloga() {
		return uloga;
	}
	public void setUloga(String uloga) {
		this.uloga = uloga;
	}
	
	
}
