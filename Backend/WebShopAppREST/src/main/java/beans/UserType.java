package beans;

public class UserType {
	private int id;
	private String name;
	private double discount;
	private int pointsNeeded;
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
	public double getDiscount() {
		return discount;
	}
	public void setDiscount(double discount) {
		this.discount = discount;
	}
	public int getPointsNeeded() {
		return pointsNeeded;
	}
	public void setPointsNeeded(int pointsNeeded) {
		this.pointsNeeded = pointsNeeded;
	}
	public UserType(int id, String name, double discount, int pointsNeeded) {
		super();
		this.id = id;
		this.name = name;
		this.discount = discount;
		this.pointsNeeded = pointsNeeded;
	}
	public UserType() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}
