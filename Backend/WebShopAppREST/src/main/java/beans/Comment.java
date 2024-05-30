package beans;

public class Comment {
	private int id;
	private int userId;
	private User user;
	private int factoryId;
	private Factory factory;
	private String comment;
	private int grade;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public int getFactoryId() {
		return factoryId;
	}
	public void setFactoryId(int factoryId) {
		this.factoryId = factoryId;
	}
	public Factory getFactory() {
		return factory;
	}
	public void setFactory(Factory factory) {
		this.factory = factory;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public int getGrade() {
		return grade;
	}
	public void setGrade(int grade) {
		this.grade = grade;
	}
	public Comment() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Comment(int id, User user, Factory factory, String comment, int grade) {
		super();
		this.id = id;
		this.user = user;
		this.factory = factory;
		this.comment = comment;
		this.grade = grade;
		this.factoryId = factory.getId();
		this.userId = user.getId();
	}
	
	
	
}
