package Gui;

import javax.swing.*;

import dcs.DB;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginWindow extends JFrame implements ActionListener {
	private Container container = getContentPane();
	private JLabel IDLabel = new JLabel("ID NUMBER");
	private JLabel passwordLabel = new JLabel("PASSWORD");
	private JTextField userTextField = new JTextField();
	private JPasswordField passwordField = new JPasswordField();
	private JButton loginButton = new JButton("LOGIN");
	private JButton registerButton = new JButton("REGISTER");
	private JButton resetButton = new JButton("RESET");
	private JCheckBox showPassword = new JCheckBox("Show Password");
	private JLabel lblDCS = new JLabel("D        C        S");

	public LoginWindow() {
		setLayoutManager();
		setFont();
		setLocationAndSize();
		addComponentsToContainer();
		addActionEvent();
		this.setTitle("DCS | Login");
		this.setVisible(true);
		this.setBounds(710, 190, 370, 600);
		this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
		this.setResizable(false);
	}

	public void setLayoutManager() {
		container.setLayout(null);
	}

	public void setLocationAndSize() {
		lblDCS.setBounds(48, 50, 325, 50);
		IDLabel.setBounds(50, 150, 100, 30);
		passwordLabel.setBounds(50, 220, 100, 30);
		userTextField.setBounds(150, 150, 150, 30);
		passwordField.setBounds(150, 220, 150, 30);
		showPassword.setBounds(150, 250, 150, 30);
		loginButton.setBounds(50, 300, 100, 30);
		registerButton.setBounds(125, 350, 100, 30);
		resetButton.setBounds(200, 300, 100, 30);
	}

	public void setFont() {
		lblDCS.setFont(new Font("Times New Roman", Font.PLAIN, 42));

	}

	public void addComponentsToContainer() {
		container.add(IDLabel);
		container.add(passwordLabel);
		container.add(userTextField);
		container.add(passwordField);
		container.add(showPassword);
		container.add(loginButton);
		container.add(registerButton);
		container.add(resetButton);
		container.add(lblDCS);

	}

	public void addActionEvent() {
		loginButton.addActionListener(this);
		resetButton.addActionListener(this);
		showPassword.addActionListener(this);
		registerButton.addActionListener(this);
	}

	void registerWindow() {
		RegisterWindow registerWindow = new RegisterWindow();
	}

	void userMainMenuWindow() {
		UserMainMenuWindow userMainMenuWindow = new UserMainMenuWindow();
	}

	void coordinationUnitMainMenuWindow() {
		CoordinationUnitMainMenuWindow coordinationUnitMainMenuWindow = new CoordinationUnitMainMenuWindow();
	}

	void personnelMainMenuWindow() {
		PersonnelMainMenuWindow personnelMainMenuWindow = new PersonnelMainMenuWindow();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
        DB dataBase = DB.getInstance();

		if (e.getSource() == loginButton) {
			String userText;
			char[] pwdText;
			userText = userTextField.getText();
			pwdText = passwordField.getPassword();

			if (dataBase.login(userText, String.valueOf(pwdText))) {
				JOptionPane.showMessageDialog(this, "Login Successful");
				dispose();
				if (dataBase.isCoordinationUnit(userText, String.valueOf(pwdText))) {
					coordinationUnitMainMenuWindow();
				} else if (dataBase.isPersonnel(userText, String.valueOf(pwdText))) {
					personnelMainMenuWindow();
				} else {
					userMainMenuWindow();
				}

			} else {
				JOptionPane.showMessageDialog(this, "Invalid Username or Password");
			}

		}
		if (e.getSource() == resetButton) {
			userTextField.setText("");
			passwordField.setText("");
		}
		if (e.getSource() == showPassword) {
			if (showPassword.isSelected()) {
				passwordField.setEchoChar((char) 0);
			} else {
				passwordField.setEchoChar('*');
			}

		}
		if (e.getSource() == registerButton) {
			registerWindow();
		}
	}
}
