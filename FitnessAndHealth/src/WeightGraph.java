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
import java.sql.ResultSet;
import Model.User;
import net.proteanit.sql.DbUtils;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.border.BevelBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class WeightGraph extends JFrame {

	private JPanel contentPane;
	private JTextField txtWeight;
	private JTable table;
	private JComboBox cbUnit;
	
	public User user;
	DatabaseHelper helper;
	

	
	
	public void setUser(User user) {
		
	}

	/**
	 * Launch the application.
	 * @param newUser 
	 */
	public void weightGraph(User newUser) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WeightGraph frame = new WeightGraph(newUser);
					frame.setTitle("Weight Graph");
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @param user2 
	 */
	public WeightGraph(User user) {
		
		this.user = user;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(280, 100, 758, 471);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txtWeight = new JTextField();
		txtWeight.setFont(new Font("Verdana", Font.PLAIN, 18));
		txtWeight.setBounds(155, 14, 122, 35);
		contentPane.add(txtWeight);
		txtWeight.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Today's Weight");
		lblNewLabel.setFont(new Font("Verdana", Font.BOLD, 14));
		lblNewLabel.setBounds(12, 13, 177, 35);
		contentPane.add(lblNewLabel);
		
		JButton btnUpdateWeight = new JButton("Update Weight");
		btnUpdateWeight.setForeground(Color.WHITE);
		btnUpdateWeight.setFont(new Font("Verdana", Font.BOLD, 14));
		btnUpdateWeight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				double weight = -1;
				
				//Check today's weight is empty or not
				if(txtWeight.getText().isEmpty()) {
					weight = -1;
					JOptionPane.showMessageDialog(null, "Fill Today's Weight");
				}else {
					weight = Double.parseDouble(txtWeight.getText());
					String unit = cbUnit.getSelectedItem().toString();
					if(unit.equals("lb")) {
						weight = convertPoundsToKilogram(weight);
					}
					int userID = user.getID();
					boolean dataExits = false;
					helper = new DatabaseHelper();
					dataExits = helper.checkcurrentDate(userID);
					if(dataExits) {
						helper.updateDaliyWeight(weight,userID);
						loadData();
					}else {
						helper.insertDaliyWeight(weight,userID);
						loadData();
					}
				}			
			}
		});
		btnUpdateWeight.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnUpdateWeight.setBackground(Color.CYAN);
		btnUpdateWeight.setBounds(377, 16, 132, 35);
		contentPane.add(btnUpdateWeight);
		
		JButton btnShowGraph = new JButton("Show Graph");
		btnShowGraph.setForeground(Color.WHITE);
		btnShowGraph.setFont(new Font("Verdana", Font.BOLD, 14));
		btnShowGraph.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int userID = user.getID();
				helper = new DatabaseHelper();
				JDBCCategoryDataset dataset = helper.graphDataset(userID);
				JFreeChart chart = ChartFactory.createLineChart("Daily Weight Graph", "Date", "Weight(in Kg)", dataset,PlotOrientation.VERTICAL,false,true,true);
				BarRenderer renderer = null;
				CategoryPlot plot = null;
				renderer = new BarRenderer();
				ChartFrame frame = new ChartFrame("Daily Weight Graph", chart);
				frame.setVisible(true);
				frame.setSize(900,420);
			}
		});
		btnShowGraph.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnShowGraph.setBackground(Color.CYAN);
		btnShowGraph.setBounds(521, 16, 184, 35);
		contentPane.add(btnShowGraph);
		
		JButton btnLoad = new JButton("Load Table");
		btnLoad.setForeground(Color.WHITE);
		btnLoad.setFont(new Font("Verdana", Font.BOLD, 14));
		btnLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				loadData();
			}
		});
		btnLoad.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnLoad.setBackground(Color.CYAN);
		btnLoad.setBounds(377, 62, 132, 35);
		contentPane.add(btnLoad);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(22, 110, 718, 311);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JButton btnShowExercisesLog = new JButton("Show Exercises Log");
		btnShowExercisesLog.setForeground(Color.WHITE);
		btnShowExercisesLog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gotoExercisesLog(user);
			}
		});
		btnShowExercisesLog.setFont(new Font("Verdana", Font.BOLD, 14));
		btnShowExercisesLog.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnShowExercisesLog.setBackground(Color.CYAN);
		btnShowExercisesLog.setBounds(521, 62, 184, 35);
		contentPane.add(btnShowExercisesLog);
		
		cbUnit = new JComboBox();
		cbUnit.setFont(new Font("Verdana", Font.PLAIN, 18));
		cbUnit.setModel(new DefaultComboBoxModel(new String[] {"kg", "lb"}));
		cbUnit.setBounds(289, 14, 56, 35);
		contentPane.add(cbUnit);
		
		setResizable(false);
	}
	
	protected double convertPoundsToKilogram(double weight) {
		// TODO Auto-generated method stub
		double newWeight = 0;
		newWeight = weight / 0.45359237;
		return newWeight;
	}
	
	public void loadData() {
		helper = new DatabaseHelper();
		ResultSet rs = helper.setTableDataset(user.getID());
		if(rs !=null) {
			table.setModel(DbUtils.resultSetToTableModel(rs));
		}
	}
	
	public void gotoExercisesLog(User user) {
		ExercisesLog graph  = new ExercisesLog(user);
		graph.exercisesLog(user);
		dispose();
	}
}
