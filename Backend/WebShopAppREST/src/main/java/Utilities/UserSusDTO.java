package Utilities;

import beans.User;

public class UserSusDTO {
	private int id;
	private String username;
	private String password;
	private String name;
	private String surname;
	private String gender;
	private String dateOfBirth;
	private String uloga;
	private int factoryId;
	private double points;
	private int userTypeId;
	private boolean blocked;
	private boolean deleted;
	private boolean suspicious;
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
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getUloga() {
		return uloga;
	}
	public void setUloga(String uloga) {
		this.uloga = uloga;
	}
	public int getFactoryId() {
		return factoryId;
	}
	public void setFactoryId(int factoryId) {
		this.factoryId = factoryId;
	}
	public double getPoints() {
		return points;
	}
	public void setPoints(double points) {
		this.points = points;
	}
	public int getUserTypeId() {
		return userTypeId;
	}
	public void setUserTypeId(int userTypeId) {
		this.userTypeId = userTypeId;
	}
	public boolean isBlocked() {
		return blocked;
	}
	public void setBlocked(boolean blocked) {
		this.blocked = blocked;
	}
	public boolean isDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	public boolean isSuspicious() {
		return suspicious;
	}
	public void setSuspicious(boolean suspicious) {
		this.suspicious = suspicious;
	}
	public UserSusDTO(User user, boolean suspicious) {
		super();
		this.id = user.getId();
		this.username = user.getUsername();
		this.password = user.getPassword();
		this.name = user.getName();
		this.surname = user.getSurname();
		this.gender = user.getGender();
		this.dateOfBirth = user.getDateOfBirth();
		this.uloga = user.getUloga();
		this.factoryId = user.getFactoryId();
		this.points = user.getPoints();
		this.userTypeId = user.getUserTypeId();
		this.blocked = user.getBlocked();
		this.deleted = user.getDeleted();
		this.suspicious = suspicious;
	}
	
}
