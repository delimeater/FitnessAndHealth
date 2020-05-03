import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Model.User;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Login extends JFrame {

	private JPanel contentPane;
	private JTextField txtUsername;
	private JPasswordField txtPassword;
	
	DatabaseHelper helper;
	

	//Launching application to login page
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setTitle("Login");
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	//Login page frame layout, initial starting page for application
	public Login() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(280, 100, 700, 550);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(224, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//Profile picture
		JLabel label = new JLabel("");
		//Default image for profile picture
		label.setIcon(new ImageIcon(Login.class.getResource("/defaultProfilePic.png")));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(49, 155, 194, 164);
		contentPane.add(label);
		
		//Introduction message on start up
		JLabel lblWelcomeToFitness = new JLabel("Welcome");
		lblWelcomeToFitness.setHorizontalAlignment(SwingConstants.CENTER);
		lblWelcomeToFitness.setForeground(Color.BLACK);
		lblWelcomeToFitness.setFont(new Font("Verdana", Font.BOLD, 30));
		lblWelcomeToFitness.setBounds(49, 332, 204, 35);
		contentPane.add(lblWelcomeToFitness);
		
		JLabel lblTo = new JLabel("to");
		lblTo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTo.setForeground(Color.BLACK);
		lblTo.setFont(new Font("Verdana", Font.BOLD, 20));
		lblTo.setBounds(49, 362, 214, 35);
		contentPane.add(lblTo);
		
		JLabel label_1 = new JLabel("Fitness & Health");
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setForeground(Color.BLACK);
		label_1.setFont(new Font("Verdana", Font.BOLD, 22));
		label_1.setBounds(49, 392, 214, 35);
		contentPane.add(label_1);
		
		//Title of page (login)
		JLabel lblLogin = new JLabel("Login Page");
		lblLogin.setHorizontalAlignment(SwingConstants.CENTER);
		lblLogin.setForeground(Color.BLACK);
		lblLogin.setFont(new Font("Verdana", Font.BOLD, 30));
		lblLogin.setBounds(49, 111, 214, 35);
		contentPane.add(lblLogin);
		
		JLabel label_3 = new JLabel("");
		label_3.setOpaque(true);
		label_3.setBackground(Color.CYAN);
		label_3.setBounds(0, 0, 306, 550);
		contentPane.add(label_3);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setForeground(Color.BLACK);
		lblUsername.setFont(new Font("Verdana", Font.BOLD, 18));
		lblUsername.setBounds(358, 149, 305, 25);
		contentPane.add(lblUsername);
		
		txtUsername = new JTextField();
		txtUsername.setFont(new Font("Verdana", Font.PLAIN, 18));
		txtUsername.setColumns(10);
		txtUsername.setBounds(358, 178, 305, 35);
		contentPane.add(txtUsername);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setForeground(Color.BLACK);
		lblPassword.setFont(new Font("Verdana", Font.BOLD, 18));
		lblPassword.setBounds(358, 229, 305, 25);
		contentPane.add(lblPassword);
		
		JLabel lblDontHaveAccout = new JLabel("Don't have Account?");
		lblDontHaveAccout.setFont(new Font("Verdana", Font.PLAIN, 14));
		lblDontHaveAccout.setBounds(358, 372, 153, 25);
		contentPane.add(lblDontHaveAccout);
		
		//Navigation to registration page
		JLabel lblRegistration = new JLabel("Register");
		lblRegistration.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				//Open registration window
				gotoRegistration();
			}
		});
		lblRegistration.setForeground(new Color(0, 191, 255));
		lblRegistration.setFont(new Font("Verdana", Font.BOLD, 14));
		lblRegistration.setBounds(510, 372, 94, 25);
		contentPane.add(lblRegistration);
		
		txtPassword = new JPasswordField();
		txtPassword.setFont(new Font("Verdana", Font.PLAIN, 18));
		txtPassword.setBounds(358, 258, 305, 35);
		contentPane.add(txtPassword);
		
		//Login icon allow user to login with username and password
		JButton btnNewButton = new JButton("Login");
		btnNewButton.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String Username = txtUsername.getText().toString();
				String Password = txtPassword.getText().toString();
				helper = new DatabaseHelper();
				User user = helper.login(Username,Password);
				if(user !=null) {
					gotoWeightGraph(user);
				}else {
					//Message user error of incorrect username/password
					JOptionPane.showMessageDialog(null, "Username or Password is incorrect!");
				}
			}
		});
		btnNewButton.setBackground(Color.CYAN);
		btnNewButton.setForeground(Color.WHITE);
		btnNewButton.setFont(new Font("Verdana", Font.BOLD, 17));
		btnNewButton.setBounds(358, 334, 200, 35);
		contentPane.add(btnNewButton);
		
		setResizable(false);
				
	}
	
	public void gotoWeightGraph(User user) {
		WeightGraph graph  = new WeightGraph(user);
		graph.weightGraph(user);
		dispose();
	}
	
	public void gotoRegistration() {
		Registration registration = new Registration();
		registration.register();
		dispose();
	}
	
	
}
