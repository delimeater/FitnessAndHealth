package Model;

public class ExerciseLogModel {
	
	private int ID;
	private int userID;
	private String exerciseName;
	private int noOfSets;
	private int noOfReps;
	private double calories;
	private String dateOfExercise;
	private int restDay;
		
	public ExerciseLogModel() {}

	public ExerciseLogModel(int userID, String exerciseName, int noOfSets, int noOfReps,double calories, String dateOfExercise,int restDay) {
		this.userID = userID;
		this.exerciseName = exerciseName;
		this.noOfSets = noOfSets;
		this.noOfReps = noOfReps;
		this.calories = calories;
		this.dateOfExercise = dateOfExercise;
		this.restDay = restDay;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public String getExerciseName() {
		return exerciseName;
	}

	public void setExerciseName(String exerciseName) {
		this.exerciseName = exerciseName;
	}

	public int getNoOfSets() {
		return noOfSets;
	}

	public void setNoOfSets(int noOfSets) {
		this.noOfSets = noOfSets;
	}

	public int getNoOfReps() {
		return noOfReps;
	}

	public void setNoOfReps(int noOfReps) {
		this.noOfReps = noOfReps;
	}

	public String getDateOfExercise() {
		return dateOfExercise;
	}

	public void setDateOfExercise(String dateOfExercise) {
		this.dateOfExercise = dateOfExercise;
	}

	public double getCalories() {
		return calories;
	}

	public void setCalories(double calories) {
		this.calories = calories;
	}

	public int getRestDay() {
		return restDay;
	}

	public void setRestDay(int restDay) {
		this.restDay = restDay;
	}
}
