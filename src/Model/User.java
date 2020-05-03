package Model;

public class User {
	
	private int ID;
	private String Username;
	private String Password;
	private String Name;
	private byte[] Image;
	private String DOB;
	private String Gender;
	private double Height;
	private double WeightGoal;
	
	public User() {}
	
	public User(String username, String password, String name, byte[] image, String dOB, String gender, double height,
			double weigthGoal) {
		super();
		this.Username = username;
		this.Password = password;
		this.Name = name;
		this.Image = image;
		this.DOB = dOB;
		this.Gender = gender;
		this.Height = height;
		this.WeightGoal = weigthGoal;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getUsername() {
		return Username;
	}

	public void setUsername(String username) {
		Username = username;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public byte[] getImage() {
		return Image;
	}

	public void setImage(byte[] image) {
		this.Image = image;
	}

	public String getDOB() {
		return DOB;
	}

	public void setDOB(String dOB) {
		DOB = dOB;
	}

	public String getGender() {
		return Gender;
	}

	public void setGender(String gender) {
		Gender = gender;
	}

	public double getHeight() {
		return Height;
	}

	public void setHeight(double height) {
		Height = height;
	}

	public double getWeightGoal() {
		return WeightGoal;
	}

	public void setWeightGoal(double weightGoal) {
		WeightGoal = weightGoal;
	}
}
