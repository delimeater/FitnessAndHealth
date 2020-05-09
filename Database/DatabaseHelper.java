import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;

import org.jfree.data.jdbc.JDBCCategoryDataset;

import Model.ExerciseLogModel;
import Model.User;

public class DatabaseHelper {
	
	private static final String DB_DRIVER_CLASS = "com.mysql.cj.jdbc.Driver";
	private static final String DB_USERNAME = "root";
	private static final String DB_PASSWORD = "";
	private static final String DB_URL = "jdbc:mysql://localhost:3306/healthandfitness?useTimezone=true&serverTimezone=UTC";
	
	private static Connection connection = null;
	
	static{
		try {
			Class.forName(DB_DRIVER_CLASS);
			connection = DriverManager.getConnection(DB_URL,DB_USERNAME,DB_PASSWORD);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void register(User user) {
		// TODO Auto-generated method stub
		
		try{
			
			String query = "INSERT INTO `login`(`Username`, `Password`, `Name`, `Image`, `DOB`, `Gender`, `Height`, `WeightGoal`) VALUES (?,?,?,?,?,?,?,?)";		
			PreparedStatement preparedStmt = connection.prepareStatement(query);
			preparedStmt.setString(1, user.getUsername());
			preparedStmt.setString(2, user.getPassword());
			preparedStmt.setString(3, user.getName());
			preparedStmt.setBytes(4, user.getImage());
			preparedStmt.setString(5, user.getDOB());
			preparedStmt.setString(6, user.getGender());
			preparedStmt.setDouble(7, user.getHeight());
			preparedStmt.setDouble(8, user.getWeightGoal());
			
			preparedStmt.execute();
			
		    JOptionPane.showMessageDialog(null, "Data Saved");
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public User login(String username, String password) {
		// TODO Auto-generated method stub
		User user = null;
		try{
			
			String query = "SELECT * FROM `login` WHERE `Username` = ? AND `Password` = ?";		
			PreparedStatement preparedStmt = connection.prepareStatement(query);
			preparedStmt.setString(1, username);
			preparedStmt.setString(2, password);
			
			ResultSet result = preparedStmt.executeQuery();
			 
	        
	 
	        if (result.next()) {
	            user = new User();
	            user.setID(result.getInt(1));
	            user.setUsername(result.getString(2));
	            user.setPassword(result.getString(3));
	            user.setName(result.getString(4));
	            user.setImage(result.getBytes(5));
	            user.setDOB(result.getString(6));
	            user.setGender(result.getString(7));
	            user.setHeight(result.getDouble(8));
	            user.setWeightGoal(result.getDouble(9));
	        }			
		    
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return user;
		
	}

	public boolean foundUser(String username) {
		// TODO Auto-generated method stub
		boolean uniqueUsername = false;
		try {
			String query = "SELECT * FROM `login` WHERE `Username` = ?";
			PreparedStatement preparedStmt = connection.prepareStatement(query);
			preparedStmt.setString(1, username);
			ResultSet result = preparedStmt.executeQuery();
			if (result.next()) {
				 uniqueUsername = true;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return uniqueUsername;
	}

	public ResultSet setTableDataset(int ID) {
		// TODO Auto-generated method stub
		ResultSet result = null;
		try {
			String query = "SELECT * FROM `dailyweight` WHERE `UserID` = ?";
			PreparedStatement preparedStmt = connection.prepareStatement(query);
			preparedStmt.setInt(1, ID);
			result = preparedStmt.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}

	public boolean checkcurrentDate(int userID) {
		// TODO Auto-generated method stub
		boolean dataExits = false;
		SimpleDateFormat sdf = new  SimpleDateFormat("yyyy/MM/dd");
		Date date  = new Date();
		String currentDate = sdf.format(date);
		try {
			String query = "SELECT *FROM `dailyweight` WHERE `UserID` = ? AND `Date` = ?";
			PreparedStatement preparedStmt = connection.prepareStatement(query);
			preparedStmt.setInt(1, userID);
			preparedStmt.setString(2, currentDate);
			ResultSet rs = preparedStmt.executeQuery();
			if(rs.next()) {
				dataExits = true;
			}
			
		    
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dataExits;
	}
	
	public void insertDaliyWeight(double weight, int userID) {
		// TODO Auto-generated method stub
		SimpleDateFormat sdf = new  SimpleDateFormat("yyyy/MM/dd");
		Date date  = new Date();
		String currentDate = sdf.format(date);
		try {
			String query = "INSERT INTO `dailyweight`(`UserID`, `Weight`, `Date`) VALUES (?,?,?)";
			PreparedStatement preparedStmt = connection.prepareStatement(query);
			preparedStmt.setInt(1, userID);
			preparedStmt.setDouble(2, weight);
			preparedStmt.setString(3, currentDate);
			preparedStmt.execute();
			
		    JOptionPane.showMessageDialog(null, "Weight Inserted!");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void updateDaliyWeight(double weight, int userID) {
		// TODO Auto-generated method stub
		SimpleDateFormat sdf = new  SimpleDateFormat("yyyy/MM/dd");
		Date date  = new Date();
		String currentDate = sdf.format(date);
		try {
			String query = "UPDATE `dailyweight` SET `Weight`= ? WHERE `UserID` = ? AND `Date` = ?";
			PreparedStatement preparedStmt = connection.prepareStatement(query);
			preparedStmt.setDouble(1, weight);
			preparedStmt.setInt(2, userID);
			preparedStmt.setString(3, currentDate);
			preparedStmt.execute();
			
		    JOptionPane.showMessageDialog(null, "Weight Updated!");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	public JDBCCategoryDataset graphDataset(int userID) {
		JDBCCategoryDataset dataset = null;
		try {
			String query= "SELECT `Date`,`Weight` FROM `dailyweight` WHERE `UserID` = "+userID;
			dataset = new JDBCCategoryDataset(connection,query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return dataset;
	}
	
	
	
	
	public void insertExerciseLog(ExerciseLogModel log) {
		// TODO Auto-generated method stub
		
		try{
			
			String query = "INSERT INTO `excerciselog`(`UserID`, `ExerciseName`, `NoOfSets`, `NoOfReps`, `Calories`, `DateOfExercise`, `RestDay`) VALUES (?,?,?,?,?,?,?)";		
			PreparedStatement preparedStmt = connection.prepareStatement(query);
			preparedStmt.setInt(1, log.getUserID());
			preparedStmt.setString(2, log.getExerciseName());
			preparedStmt.setInt(3, log.getNoOfSets());
			preparedStmt.setInt(4, log.getNoOfReps());
			preparedStmt.setDouble(5, log.getCalories());
			preparedStmt.setString(6, log.getDateOfExercise());
			preparedStmt.setInt(7, log.getRestDay());
			
			preparedStmt.execute();
			
		    JOptionPane.showMessageDialog(null, "Data Saved");
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void updateExerciseLog(ExerciseLogModel log) {
		// TODO Auto-generated method stub
		
		try{
			
			String query = "UPDATE `excerciselog` SET `UserID`=?,`ExerciseName`=?,`NoOfSets`=?,`NoOfReps`=?,`Calories`=?,`DateOfExercise`=?,`RestDay`=? WHERE `ID`=?";		
			PreparedStatement preparedStmt = connection.prepareStatement(query);
			preparedStmt.setInt(1, log.getUserID());
			preparedStmt.setString(2, log.getExerciseName());
			preparedStmt.setInt(3, log.getNoOfSets());
			preparedStmt.setInt(4, log.getNoOfReps());
			preparedStmt.setDouble(5, log.getCalories());
			preparedStmt.setString(6, log.getDateOfExercise());
			preparedStmt.setInt(7, log.getRestDay());
			
			preparedStmt.execute();
			
		    JOptionPane.showMessageDialog(null, "Data Updated");
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public ExerciseLogModel getExerciseLogModel(int ID) {
		ExerciseLogModel log = null;
		try {
			String query = "SELECT * FROM `excerciselog` WHERE `ID` = ?";
			PreparedStatement preparedStmt = connection.prepareStatement(query);
			preparedStmt.setInt(1, ID);
			ResultSet result = preparedStmt.executeQuery();
			if(result.next()) {
				log.setID(result.getInt(1));
				log.setUserID(result.getInt(2));
				log.setExerciseName(result.getString(3));
				log.setNoOfSets(result.getInt(4));
				log.setNoOfReps(result.getInt(5));
				log.setCalories(result.getDouble(6));
				log.setDateOfExercise(result.getString(7));
				log.setRestDay(result.getInt(8));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return log;
	}
	
	public ResultSet setExerciseTableDataset(int ID) {
		// TODO Auto-generated method stub
		ResultSet result = null;
		try {
			String query = "SELECT * FROM `excerciselog` WHERE `UserID` = ?";
			PreparedStatement preparedStmt = connection.prepareStatement(query);
			preparedStmt.setInt(1, ID);
			result = preparedStmt.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	
	public JDBCCategoryDataset caloriGraphDataset(int userID) {
		JDBCCategoryDataset dataset = null;
		try {
			String query= "SELECT `DateOfExercise`,sum(`Calories`) as Calories FROM `excerciselog` WHERE `UserID` =" +userID+ " GROUP BY `DateOfExercise`";
			dataset = new JDBCCategoryDataset(connection,query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return dataset;
	}
	
	public boolean checkForRestDay() {
		boolean restDay = false;
		SimpleDateFormat sdf = new  SimpleDateFormat("yyyy/MM/dd");
		Date date  = new Date();
		String dateOfExercise = sdf.format(date);
		try {
			String query = "SELECT * FROM `excerciselog` WHERE `RestDay` = ? AND `DateOfExercise` = ?";
			PreparedStatement preparedStmt = connection.prepareStatement(query);
			preparedStmt.setInt(1, 1);
			preparedStmt.setString(2, dateOfExercise);
			ResultSet result = preparedStmt.executeQuery();
			if(result.next()) {
				restDay = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return restDay;
	}
	
	public boolean checkDataExist(String dateOfExercise) {
		boolean dataExist = false;
		try {
			String query = "SELECT * FROM `excerciselog` WHERE `DateOfExercise` = ?";
			PreparedStatement preparedStmt = connection.prepareStatement(query);
			preparedStmt.setString(1, dateOfExercise);
			ResultSet result = preparedStmt.executeQuery();
			if(result.next()) {
				dataExist = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dataExist;
	}

	public JDBCCategoryDataset caloriGraphDataset1(int userID) {
		// TODO Auto-generated method stub
		return null;
	}
}
