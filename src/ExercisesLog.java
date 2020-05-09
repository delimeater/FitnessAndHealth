import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.jdbc.JDBCCategoryDataset;

import Model.ExerciseLogModel;
import Model.User;
import net.proteanit.sql.DbUtils;

import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Color;
import javax.swing.border.BevelBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ExercisesLog extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JTextField txtExerciseName;
	private JLabel lblNumberOfSets;
	private JTextField txtNumberOfSets;
	private JLabel lblNumberOfRep;
	private JTextField txtNumberOfRep;
	private JButton btnSave;
	private JButton btnUpdate;
	private JButton btnLoadData;
	private JCheckBox cbRestDay;
	private JTextField txtCaloriesBurn;
	
	public User user;
	DatabaseHelper helper;
	String oldDateOfExercise = null;
	

	//Launch the application.
	public void exercisesLog(User newUser) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ExercisesLog frame = new ExercisesLog(newUser);
					frame.setTitle("Excercises Log");
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	//Framework of exercise log
	public ExercisesLog(User user) {
		
		this.user = user;
		helper  = new DatabaseHelper();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 947, 474);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(233, 13, 684, 415);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int row = table.getSelectedRow();
				int ID = Integer.parseInt(table.getModel().getValueAt(row, 0).toString());
				ExerciseLogModel log = helper.getExerciseLogModel(ID);
				setFields(log);
				oldDateOfExercise = log.getDateOfExercise();
			}
		});
		scrollPane.setViewportView(table);
		//Button to navigate back to weight graph
		JButton btnGotoWeightGraph = new JButton("Go to Weight Graph");
		btnGotoWeightGraph.setForeground(Color.WHITE);
		btnGotoWeightGraph.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gotoWeightGraph(user);
			}
		});
		btnGotoWeightGraph.setFont(new Font("Verdana", Font.BOLD, 14));
		btnGotoWeightGraph.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnGotoWeightGraph.setBackground(Color.CYAN);
		btnGotoWeightGraph.setBounds(11, 393, 210, 35);
		contentPane.add(btnGotoWeightGraph);
		
		//Labels
		JLabel lblExerciseName = new JLabel("Exercise Name");
		lblExerciseName.setFont(new Font("Verdana", Font.BOLD, 16));
		lblExerciseName.setBounds(11, 13, 209, 20);
		contentPane.add(lblExerciseName);
		
		txtExerciseName = new JTextField();
		txtExerciseName.setBounds(12, 40, 208, 25);
		contentPane.add(txtExerciseName);
		txtExerciseName.setColumns(10);
		
		lblNumberOfSets = new JLabel("Number Of Sets");
		lblNumberOfSets.setFont(new Font("Verdana", Font.BOLD, 16));
		lblNumberOfSets.setBounds(11, 78, 209, 20);
		contentPane.add(lblNumberOfSets);
		
		txtNumberOfSets = new JTextField();
		txtNumberOfSets.setColumns(10);
		txtNumberOfSets.setBounds(12, 105, 208, 25);
		contentPane.add(txtNumberOfSets);
		
		lblNumberOfRep = new JLabel("Number Of Rep");
		lblNumberOfRep.setFont(new Font("Verdana", Font.BOLD, 16));
		lblNumberOfRep.setBounds(12, 143, 208, 20);
		contentPane.add(lblNumberOfRep);
		
		txtNumberOfRep = new JTextField();
		txtNumberOfRep.setColumns(10);
		txtNumberOfRep.setBounds(13, 170, 207, 25);
		contentPane.add(txtNumberOfRep);
		
		//Save exercise to database
		btnSave = new JButton("Save");
		btnSave.setForeground(Color.WHITE);
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int userID = user.getID();
				String exerciseName = txtExerciseName.getText();
				int noOfSets = Integer.parseInt(txtNumberOfSets.getText());
				int noOfReps = Integer.parseInt(txtNumberOfRep.getText());
				double calories = Integer.parseInt(txtCaloriesBurn.getText());
				SimpleDateFormat sdf = new  SimpleDateFormat("yyyy/MM/dd");
				Date date  = new Date();
				String dateOfExercise = sdf.format(date);
				int restDay = 0;
				boolean restDayCheck = helper.checkForRestDay();
				if(restDayCheck) {
					JOptionPane.showMessageDialog(null, "Sorry!!!!\nYou can't set "+dateOfExercise+" to be Rest day");
				}else {
					if(cbRestDay.isSelected()) {
						boolean dataExist = helper.checkDataExist(dateOfExercise); 
						if(!dataExist) {
							restDay = 1;
							ExerciseLogModel log = new ExerciseLogModel(userID,"N/A",0,0,0,dateOfExercise,restDay);
							helper.insertExerciseLog(log);
							loadData();
							clearFields();
						}else {
							JOptionPane.showMessageDialog(null, "Sorry!!!!\nYou can't set "+dateOfExercise+" to be Rest day");
						}
						
					}else {
						ExerciseLogModel log = new ExerciseLogModel(userID,exerciseName,noOfSets,noOfReps,calories,dateOfExercise,restDay);
						helper.insertExerciseLog(log);
						loadData();
						clearFields();
					}
				}
				
			}
		});
		btnSave.setFont(new Font("Verdana", Font.BOLD, 14));
		btnSave.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnSave.setBackground(Color.CYAN);
		btnSave.setBounds(11, 304, 89, 35);
		contentPane.add(btnSave);
		
		//Update exercise in database
		btnUpdate = new JButton("Update");
		btnUpdate.setForeground(Color.WHITE);
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int userID = user.getID();
				String exerciseName = txtExerciseName.getText();
				int noOfSets = Integer.parseInt(txtNumberOfSets.getText());
				int noOfReps = Integer.parseInt(txtNumberOfRep.getText());
				double calories = Integer.parseInt(txtCaloriesBurn.getText());
				int restDay = 0;
				if(cbRestDay.isSelected()) {
					boolean dataExist = helper.checkDataExist(oldDateOfExercise); 
					if(!dataExist) {
						restDay = 1;
						ExerciseLogModel log = new ExerciseLogModel(userID,"N/A",0,0,0,oldDateOfExercise,restDay);
						helper.insertExerciseLog(log);
						loadData();
						clearFields();
					}else {
						JOptionPane.showMessageDialog(null, "Sorry!!!!\nYou can't set "+oldDateOfExercise+" to be Rest day");
					}
				}else {
					ExerciseLogModel log = new ExerciseLogModel(userID,exerciseName,noOfSets,noOfReps,calories,oldDateOfExercise,restDay);
					helper.updateExerciseLog(log);
					clearFields();
				}
			}
		});
		btnUpdate.setFont(new Font("Verdana", Font.BOLD, 14));
		btnUpdate.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnUpdate.setBackground(Color.CYAN);
		btnUpdate.setBounds(112, 304, 109, 35);
		contentPane.add(btnUpdate);
		
		btnLoadData = new JButton("Load Data");
		btnLoadData.setForeground(Color.WHITE);
		btnLoadData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadData();
			}
		});
		btnLoadData.setFont(new Font("Verdana", Font.BOLD, 14));
		btnLoadData.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnLoadData.setBackground(Color.CYAN);
		btnLoadData.setBounds(11, 348, 89, 35);
		contentPane.add(btnLoadData);
		
		cbRestDay = new JCheckBox("Rest Day");
		cbRestDay.setFont(new Font("Verdana", Font.BOLD, 16));
		cbRestDay.setBounds(11, 270, 209, 25);
		contentPane.add(cbRestDay);
		
		JLabel lblCaloriesBurn = new JLabel("Calories Burn");
		lblCaloriesBurn.setFont(new Font("Verdana", Font.BOLD, 16));
		lblCaloriesBurn.setBounds(12, 208, 208, 20);
		contentPane.add(lblCaloriesBurn);
		
		txtCaloriesBurn = new JTextField();
		txtCaloriesBurn.setColumns(10);
		txtCaloriesBurn.setBounds(13, 235, 207, 25);
		contentPane.add(txtCaloriesBurn);
		
		//Button to display graph
		JButton btnShowGraph = new JButton("Show Graph");
		btnShowGraph.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int userID = user.getID();
				helper = new DatabaseHelper();
				JDBCCategoryDataset dataset = helper.caloriGraphDataset(userID);
				JFreeChart chart = ChartFactory.createLineChart("Daily Calories Burned Graph", "Date", "Calories", dataset,PlotOrientation.VERTICAL,false,true,true);
				BarRenderer renderer = null;
				CategoryPlot plot = null;
				renderer = new BarRenderer();
				ChartFrame frame = new ChartFrame("Daily Calories Burned Graph", chart);
				frame.setVisible(true);
				frame.setSize(900,420);
			}
		});
		btnShowGraph.setForeground(Color.WHITE);
		btnShowGraph.setFont(new Font("Verdana", Font.BOLD, 14));
		btnShowGraph.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnShowGraph.setBackground(Color.CYAN);
		btnShowGraph.setBounds(112, 345, 109, 35);
		contentPane.add(btnShowGraph);
		
		setResizable(false);
	}
	
	public void clearFields() {
		txtExerciseName.setText("");
		txtNumberOfSets.setText("");
		txtNumberOfRep.setText("");
		txtCaloriesBurn.setText("");
		cbRestDay.setSelected(false);
	}
	
	public void setFields(ExerciseLogModel log) {
		txtExerciseName.setText(log.getExerciseName());
		txtNumberOfSets.setText(""+log.getNoOfSets());
		txtNumberOfRep.setText(""+log.getNoOfReps());
		txtCaloriesBurn.setText(""+log.getCalories());
		if(log.getRestDay() == 1) {
			cbRestDay.setSelected(true);
		}else {
			cbRestDay.setSelected(false);
		}
		
	}
	
	//Load data
	public void loadData() {
		helper = new DatabaseHelper();
		ResultSet rs = helper.setExerciseTableDataset(user.getID());
		if(rs !=null) {
			table.setModel(DbUtils.resultSetToTableModel(rs));
		}
	}
	
	public void gotoWeightGraph(User user) {
		WeightGraph graph  = new WeightGraph(user);
		graph.weightGraph(user);
		dispose();
	}
}
