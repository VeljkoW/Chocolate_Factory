package beans;

public class Comment {
	private int id;
	private int userId;
	private int factoryId;
	private String comment;
	private int grade;
	private String status;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
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
	public int getFactoryId() {
		return factoryId;
	}
	public void setFactoryId(int factoryId) {
		this.factoryId = factoryId;
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
	public Comment(int id, int userId, int factoryId, String comment, int grade,String status) {
		super();
		this.id = id;
		this.comment = comment;
		this.grade = grade;
		this.factoryId = factoryId;
		this.userId = userId;
		this.status=status;
	}
	
	
	
}
