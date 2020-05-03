import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.border.MatteBorder;
import javax.swing.border.BevelBorder;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import Model.User;

import com.toedter.calendar.JDateChooser;
import javax.swing.JPasswordField;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Registration extends JFrame {

	private JPanel contentPane;
	String filename;
	byte[] profile_pic = null;
	private JTextField txtUserame;
	private JTextField txtName;
	private JTextField txtHeight;
	private JTextField txtWeightGoal;
	private JPasswordField txtPassword;
	private JComboBox cbUnit;
	

	//Registration Launch
	public void register() {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Registration frame = new Registration();
					frame.setTitle("Registration");
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	//Registration page frame layout
	public Registration() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(280, 100, 900, 750);
		contentPane = new JPanel();
		contentPane.setForeground(new Color(0, 0, 0));
		contentPane.setBackground(new Color(224, 255, 255));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//Title of page (Registration)
		JLabel lblRegistration = new JLabel("Registration Page");
		lblRegistration.setForeground(Color.BLACK);
		lblRegistration.setFont(new Font("Verdana", Font.BOLD, 20));
		lblRegistration.setBounds(76, 237, 214, 35);
		contentPane.add(lblRegistration);
		
		JLabel lblProfilePic = new JLabel("");
		
		//Inserting profile image of user
		lblProfilePic.setHorizontalAlignment(SwingConstants.CENTER);
		//Default image for profile picture
		lblProfilePic.setIcon(new ImageIcon(Registration.class.getResource("/defaultProfilePic.png")));
		lblProfilePic.setBounds(87, 299, 194, 164);
		contentPane.add(lblProfilePic);
		
		//User clicks profile pic icon change or insert profile image
		lblProfilePic.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				JFileChooser chooser = new JFileChooser();
				chooser.showOpenDialog(null);
				File file = chooser.getSelectedFile();
				filename = file.getAbsolutePath();
				ImageIcon imageIcon = new ImageIcon(new ImageIcon(filename).getImage().getScaledInstance(lblProfilePic.getWidth(), lblProfilePic.getHeight(),Image.SCALE_SMOOTH));
				lblProfilePic.setIcon(imageIcon);
				
				
				try {
					File image = new File(filename);
					FileImageInputStream fis = new FileImageInputStream(image);
					ByteArrayOutputStream bos = new ByteArrayOutputStream();
					byte []buf = new byte[1024];
					for(int readNum;(readNum = fis.read(buf)) != -1;) {
						bos.write(buf,0,readNum);
					}
					profile_pic = bos.toByteArray(); 
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			}
		});
		
		//Message user option to change profile picture
		JLabel lblWelcome = new JLabel("Insert Profile Image");
		lblWelcome.setForeground(Color.BLACK);
		lblWelcome.setFont(new Font("Verdana", Font.BOLD, 20));
		lblWelcome.setBounds(60, 476, 253, 43);
		contentPane.add(lblWelcome);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setOpaque(true);
		lblNewLabel.setBackground(new Color(0, 255, 255));
		lblNewLabel.setBounds(0, 0, 377, 750);
		contentPane.add(lblNewLabel);
		
		txtUserame = new JTextField();
		
		txtUserame.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				boolean foundUser = false;
				String Username = txtUserame.getText();
				DatabaseHelper helper = new DatabaseHelper();
				foundUser = helper.foundUser(Username);
				if(foundUser) {
					txtUserame.setBorder(new MatteBorder(2, 2, 2, 2, (Color) new Color(255, 0, 0)));
					//Message user error of taken username
					JOptionPane.showMessageDialog(null, Username+"Username already used!");
				}else {
					txtUserame.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
				}
			}
		});
		txtUserame.setFont(new Font("Verdana", Font.PLAIN, 18));
		txtUserame.setBounds(459, 76, 400, 35);
		contentPane.add(txtUserame);
		txtUserame.setColumns(10);
		
		txtName = new JTextField();
		txtName.setFont(new Font("Verdana", Font.PLAIN, 18));
		txtName.setColumns(10);
		txtName.setBounds(459, 236, 400, 35);
		contentPane.add(txtName);
		
		//Subtitles for text boxes
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setForeground(new Color(0, 0, 0));
		lblUsername.setFont(new Font("Verdana", Font.BOLD, 18));
		lblUsername.setBounds(459, 47, 400, 25);
		contentPane.add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setForeground(new Color(0, 0, 0));
		lblPassword.setFont(new Font("Verdana", Font.BOLD, 18));
		lblPassword.setBounds(459, 127, 400, 25);
		contentPane.add(lblPassword);
		
		JLabel lblName = new JLabel("Name");
		lblName.setForeground(new Color(0, 0, 0));
		lblName.setFont(new Font("Verdana", Font.BOLD, 18));
		lblName.setBounds(459, 207, 400, 25);
		contentPane.add(lblName);
		
		JLabel lblDOB = new JLabel("Date Of Birth");
		lblDOB.setForeground(new Color(0, 0, 0));
		lblDOB.setFont(new Font("Verdana", Font.BOLD, 18));
		lblDOB.setBounds(459, 287, 400, 25);
		contentPane.add(lblDOB);
		
		JLabel lblGender = new JLabel("Gender");
		lblGender.setForeground(new Color(0, 0, 0));
		lblGender.setFont(new Font("Verdana", Font.BOLD, 18));
		lblGender.setBounds(459, 367, 400, 25);
		contentPane.add(lblGender);
		
		
		
		JLabel lblWeightGoal = new JLabel("Height (ft)");
		lblWeightGoal.setForeground(new Color(0, 0, 0));
		lblWeightGoal.setFont(new Font("Verdana", Font.BOLD, 18));
		lblWeightGoal.setBounds(459, 447, 400, 25);
		contentPane.add(lblWeightGoal);
		
		txtHeight = new JTextField();
		txtHeight.setFont(new Font("Verdana", Font.PLAIN, 18));
		txtHeight.setColumns(10);
		txtHeight.setBounds(459, 476, 312, 35);
		contentPane.add(txtHeight);
		
		JLabel lblWeightGoal_1 = new JLabel("Weight Goal");
		lblWeightGoal_1.setForeground(new Color(0, 0, 0));
		lblWeightGoal_1.setFont(new Font("Verdana", Font.BOLD, 18));
		lblWeightGoal_1.setBounds(459, 527, 400, 25);
		contentPane.add(lblWeightGoal_1);
		
		txtWeightGoal = new JTextField();
		txtWeightGoal.setFont(new Font("Verdana", Font.PLAIN, 18));
		txtWeightGoal.setColumns(10);
		txtWeightGoal.setBounds(459, 556, 400, 35);
		contentPane.add(txtWeightGoal);
		
				
		//Gender drop box
		JComboBox txtGender = new JComboBox();
		txtGender.setModel(new DefaultComboBoxModel(new String[] {"Male", "Female"}));
		txtGender.setEditable(true);
		JTextField text = ((JTextField) txtGender.getEditor().getEditorComponent());
        text.setBackground(Color.WHITE);
		txtGender.setBackground(new Color(255, 255, 255));
		txtGender.setFont(new Font("Verdana", Font.PLAIN, 18));
		txtGender.setBounds(459, 396, 400, 35);
		contentPane.add(txtGender);
		
		//user choose DOB from calendar
		JDateChooser txtDOB = new JDateChooser();
		txtDOB.setDateFormatString("MMMM dd, yyyy");
		txtDOB.setFont(new Font("Verdana", Font.PLAIN, 18));
		txtDOB.setOpaque(false);
		txtDOB.setBackground(new Color(224, 255, 255));
		txtDOB.setBounds(459, 316, 400, 35);
		contentPane.add(txtDOB);
		
		JLabel lblNewLabel_1 = new JLabel("Already have Account");
		lblNewLabel_1.setFont(new Font("Verdana", Font.PLAIN, 14));
		lblNewLabel_1.setBounds(459, 602, 153, 25);
		contentPane.add(lblNewLabel_1);
		
		//Navigation to back to login page
		JLabel lblNewLabel_2 = new JLabel("Login");
		lblNewLabel_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				gotoLogin();
			}
		});
		lblNewLabel_2.setForeground(new Color(0, 191, 255));
		lblNewLabel_2.setFont(new Font("Verdana", Font.BOLD, 14));
		lblNewLabel_2.setBounds(630, 602, 56, 25);
		contentPane.add(lblNewLabel_2);
		
		txtPassword = new JPasswordField();
		txtPassword.setFont(new Font("Verdana", Font.PLAIN, 18));
		txtPassword.setBounds(459, 156, 400, 35);
		contentPane.add(txtPassword);
		
		JButton btnNewButton = new JButton("Registration");
		btnNewButton.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				boolean foundUser = false;
				DatabaseHelper helper = new DatabaseHelper();
				String Username = txtUserame.getText().toString();
				
				String Password = txtPassword.getText().toString();
				String Name = txtName.getText().toString();
				
				if(profile_pic == null) {
					try {
						Path path  = Paths.get("Images/defaultProfilePic.png");
						Path realPath = path.toRealPath(LinkOption.NOFOLLOW_LINKS);
						BufferedImage bufferedImage = ImageIO.read(realPath.toFile());

						// get DataBufferBytes from Raster
						WritableRaster raster = bufferedImage .getRaster();
						DataBufferByte data   = (DataBufferByte) raster.getDataBuffer();
						profile_pic = data.getData();

					} catch (Exception ex) {
						// TODO Auto-generated catch block
						ex.printStackTrace();
					}
				    
				}
				
				byte[] Image = profile_pic;
				SimpleDateFormat sdf = new  SimpleDateFormat("yyyy/MM/dd");
				Date date  = txtDOB.getDate();
				String DOB = sdf.format(date);
				String Gender = txtGender.getSelectedItem().toString();
				double Height = Double.parseDouble(txtHeight.getText());
				double WeightGoal = Double.parseDouble(txtWeightGoal.getText());
				String unit = cbUnit.getSelectedItem().toString();
				if(unit.equals("lb")) {
					WeightGoal = convertPoundsToKilogram(WeightGoal);
				}
				User user = new User(Username,Password,Name,Image,DOB,Gender,Height,WeightGoal);
				foundUser = helper.foundUser(Username);
				if(foundUser) {
					txtUserame.setBorder(new MatteBorder(2, 2, 2, 2, (Color) new Color(255, 0, 0)));
					JOptionPane.showMessageDialog(null, Username+"Username already used!");
				}else {
					helper.register(user);
					gotoLogin();
				}
			}
		});
		btnNewButton.setBackground(Color.CYAN);
		btnNewButton.setForeground(Color.WHITE);
		btnNewButton.setFont(new Font("Verdana", Font.BOLD, 17));
		btnNewButton.setBounds(459, 640, 200, 35);
		contentPane.add(btnNewButton);
		
		//Weight drop box
		cbUnit = new JComboBox();
		cbUnit.setFont(new Font("Verdana", Font.PLAIN, 18));
		cbUnit.setModel(new DefaultComboBoxModel(new String[] {"lb"}));
		cbUnit.setBounds(783, 476, 76, 35);
		contentPane.add(cbUnit);
		
		setResizable(false);
	}
	
	//Weight conversion
	protected double convertPoundsToKilogram(double weight) {
		// TODO Auto-generated method stub
		double newWeight = 0;
		//Formula (https://www.metric-conversions.org/weight/pounds-to-kilograms.htm)
		newWeight = weight / 2.2046;
		return newWeight;
	}
	
	public void gotoLogin() {
		Login.main(null);
		dispose();
	}
	
}
