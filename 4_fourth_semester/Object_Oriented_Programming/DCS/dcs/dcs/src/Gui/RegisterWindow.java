package Gui;

import javax.swing.*;

import dcs.DB;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.regex.Pattern;

public class RegisterWindow extends JFrame implements ActionListener {

	private Container container = getContentPane();
	private JLabel firstNameLabel = new JLabel("First Name");
	private JLabel lastNameLabel = new JLabel("Last Name");
	private JLabel passwordLabel = new JLabel("Password");
	private JLabel phoneNumberLabel = new JLabel("Phone Number");
	private JLabel emailLabel = new JLabel("Email");
	private JLabel IDnumberLabel = new JLabel("ID Number");
	private JLabel lblNewUserRegister = new JLabel("New User Register");

	private JTextField firstNameField = new JTextField();
	private JTextField lastNameField = new JTextField();
	private JPasswordField passwordField = new JPasswordField();
	private JTextField phoneNumberField = new JTextField();
	private JTextField emailField = new JTextField();
	private JTextField IDnumberField = new JTextField();

	private JButton registerButton = new JButton("REGISTER");
	private JButton btnNewButton = new JButton("Ok");

	private JCheckBox showPassword = new JCheckBox("Show Password");

	private static final String EMAIL_REGEX = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
	private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
	private static final String POSITIVE_NUMBERS = "^[1-9]\\d*$";

	private boolean isValidEmail(String email) {
		return EMAIL_PATTERN.matcher(email).matches();
	}

	private boolean isValidID(String str) {
		if (str == null) {
			return false;
		}
		try {
			if (str.length() == 11 && str.matches(POSITIVE_NUMBERS)) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}

	private boolean isValidPhoneNumber(String str) {
		if (str == null) {
			return false;
		}
		try {
			if (str.length() == 10 && str.matches(POSITIVE_NUMBERS)) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}

	public RegisterWindow() {
		setLayoutManager();
		setLocationAndSize();
		addComponentsToContainer();
		addActionEvent();
		setFont();
		setColumns();
		this.setTitle("DCS | REGISTER");
		this.setVisible(true);
		this.setBounds(450, 190, 1014, 597);
		this.setResizable(false);
	}

	public void setLayoutManager() {
		container.setLayout(null);
	}

	public void setLocationAndSize() {
		lblNewUserRegister.setBounds(362, 52, 325, 50);

		firstNameLabel.setBounds(58, 152, 99, 43);
		lastNameLabel.setBounds(58, 243, 110, 29);
		emailLabel.setBounds(58, 324, 124, 36);
		IDnumberLabel.setBounds(542, 159, 99, 29);
		passwordLabel.setBounds(542, 245, 99, 24);
		phoneNumberLabel.setBounds(542, 329, 139, 26);

		firstNameField.setBounds(214, 151, 228, 50);
		lastNameField.setBounds(214, 235, 228, 50);
		emailField.setBounds(214, 320, 228, 50);
		IDnumberField.setBounds(707, 151, 228, 50);
		passwordField.setBounds(707, 235, 228, 50);
		phoneNumberField.setBounds(707, 320, 228, 50);

		btnNewButton.setBounds(399, 447, 259, 74);
		registerButton.setBounds(399, 447, 259, 74);
		showPassword.setBounds(539, 275, 150, 30);

	}

	public void setFont() {
		lblNewUserRegister.setFont(new Font("Times New Roman", Font.PLAIN, 42));

		firstNameLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		firstNameField.setFont(new Font("Tahoma", Font.PLAIN, 32));
		lastNameLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lastNameField.setFont(new Font("Tahoma", Font.PLAIN, 32));
		emailLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		emailField.setFont(new Font("Tahoma", Font.PLAIN, 32));
		IDnumberLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		IDnumberField.setFont(new Font("Tahoma", Font.PLAIN, 32));
		passwordLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		passwordField.setFont(new Font("Tahoma", Font.PLAIN, 32));
		phoneNumberLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		phoneNumberField.setFont(new Font("Tahoma", Font.PLAIN, 32));
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 22));
		registerButton.setFont(new Font("Tahoma", Font.PLAIN, 22));
	}

	public void setColumns() {
		firstNameField.setColumns(10);
		lastNameField.setColumns(10);
		emailField.setColumns(10);
		IDnumberField.setColumns(10);
		phoneNumberField.setColumns(10);
	}

	public void addComponentsToContainer() {
		container.add(firstNameLabel);
		container.add(lastNameLabel);
		container.add(emailLabel);
		container.add(IDnumberLabel);
		container.add(passwordLabel);
		container.add(phoneNumberLabel);
		container.add(registerButton);
		container.add(showPassword);
		container.add(firstNameField);
		container.add(lastNameField);
		container.add(emailField);
		container.add(IDnumberField);
		container.add(passwordField);
		container.add(phoneNumberField);
		container.add(lblNewUserRegister);

	}

	public void addActionEvent() {
		registerButton.addActionListener(this);
		showPassword.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
        DB dataBase = DB.getInstance();

		if (e.getSource() == registerButton) {
			String firstName = firstNameField.getText();
			String lastName = lastNameField.getText();
			String email = emailField.getText();
			String IDnumber = IDnumberField.getText();
			String mobileNumber = phoneNumberField.getText();
			char[] password = passwordField.getPassword();

			String msg = "" + firstName;
			msg += " \n";
			if (!isValidPhoneNumber(mobileNumber)) {
				JOptionPane.showMessageDialog(btnNewButton, "Enter a valid mobile number (5-- --- ----).");
			}
			if (firstName.equalsIgnoreCase("")) {
				JOptionPane.showMessageDialog(btnNewButton, "Name is a mandatory field.");
			}
			if (!isValidEmail(email)) {
				JOptionPane.showMessageDialog(btnNewButton, "Please enter a valid email.");
			}
			if (!isValidID(IDnumber)) {
				JOptionPane.showMessageDialog(btnNewButton, "Invalid ID");
			}
			if (lastName.equalsIgnoreCase("")) {
				JOptionPane.showMessageDialog(btnNewButton, "Last Name is a mandatory field.");
			}
			if (String.valueOf(password).length() < 6) {
				JOptionPane.showMessageDialog(btnNewButton, "Password must be longer than 6 characters");
			}
			if (!(firstName.equalsIgnoreCase("") || !isValidEmail(email) || !isValidID(IDnumber)
					|| lastName.equalsIgnoreCase("") || String.valueOf(password).length() < 6 
					|| !isValidPhoneNumber(mobileNumber))) {

				if (dataBase.InsertUser(firstName, lastName, String.valueOf(password), mobileNumber, email, IDnumber)) {
					JOptionPane.showMessageDialog(btnNewButton,
							"Welcome, " + msg + "Your account is sucessfully created");
					dispose();
				} else {
					JOptionPane.showMessageDialog(btnNewButton, "This ID already exist");
				}
			}

		}

		if (e.getSource() == showPassword) {
			if (showPassword.isSelected()) {
				passwordField.setEchoChar((char) 0);
			} else {
				passwordField.setEchoChar('*');
			}

		}

	}

}
