package beans;

public class Chocolate {
	private int id;
	private int name;
	private double price;
	private String variety;
	private int factoryId;
	private String type;
	private double mass;
	private String description;
	private String imagePath;	//this needs to change aaaaaaaaaaaaaaaa
	private String status;
	private int stock;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getName() {
		return name;
	}
	public void setName(int name) {
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
	public Chocolate(int id, int name, double price, String variety, int factoryId, String type, double mass,
			String description, String imagePath, String status, int stock) {
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
	}
	public Chocolate() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
}
