package Gui;

import javax.swing.*;

import dcs.DB;
import dcs.Dcs;
import dcs.PersonalInfo;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.regex.Pattern;

public class UserEditPersonelInfoWindow extends JFrame implements ActionListener {

	private Container container = getContentPane();
	private JLabel passwordLabel = new JLabel("Password");
	private JLabel phoneNumberLabel = new JLabel("Phone Number");
	private JLabel emailLabel = new JLabel("Email");
	private JLabel lblEditInfo = new JLabel("Edit Information");

	private JPasswordField passwordField = new JPasswordField();
	private JTextField phoneNumberField = new JTextField();
	private JTextField emailField = new JTextField();

	private JButton registerButton = new JButton("UPDATE");
	private JButton btnNewButton = new JButton("Ok");

	private JCheckBox showPassword = new JCheckBox("Show Password");

	private static final String EMAIL_REGEX = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
	private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
	private static final String POSITIVE_NUMBERS = "^[1-9]\\d*$";

	private boolean isValidEmail(String email) {
		return EMAIL_PATTERN.matcher(email).matches();
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

	public UserEditPersonelInfoWindow() {
		PersonalInfo info = Dcs.currentUser.getPersonalInfo();
		passwordField.setText(info.getPassword());
		phoneNumberField.setText(info.getPhoneNumber());
		emailField.setText(info.getEmail());
		setLayoutManager();
		setLocationAndSize();
		addComponentsToContainer();
		addActionEvent();
		setFont();
		setColumns();
		this.setTitle("DCS | UPDATE INFO");
		this.setVisible(true);
		this.setBounds(450, 190, 1014, 597);
		this.setResizable(false);
	}

	public void setLayoutManager() {
		container.setLayout(null);
	}

	public void setLocationAndSize() {
		lblEditInfo.setBounds(362, 52, 325, 50);

		emailLabel.setBounds(58, 245, 99, 24);
		passwordLabel.setBounds(58, 324, 124, 36);
		phoneNumberLabel.setBounds(542, 329, 139, 26);

		emailField.setBounds(214, 235, 720, 50);
		passwordField.setBounds(214, 320, 228, 50);
		phoneNumberField.setBounds(707, 320, 228, 50);

		btnNewButton.setBounds(399, 447, 259, 74);
		registerButton.setBounds(399, 447, 259, 74);
		showPassword.setBounds(55, 354, 150, 30);

	}

	public void setFont() {
		lblEditInfo.setFont(new Font("Times New Roman", Font.PLAIN, 42));

		emailLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		emailField.setFont(new Font("Tahoma", Font.PLAIN, 32));
		passwordLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		passwordField.setFont(new Font("Tahoma", Font.PLAIN, 32));
		phoneNumberLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		phoneNumberField.setFont(new Font("Tahoma", Font.PLAIN, 32));
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 22));
		registerButton.setFont(new Font("Tahoma", Font.PLAIN, 22));
	}

	public void setColumns() {
		emailField.setColumns(10);
		phoneNumberField.setColumns(10);
	}

	public void addComponentsToContainer() {
		container.add(emailLabel);
		container.add(passwordLabel);
		container.add(phoneNumberLabel);
		container.add(registerButton);
		container.add(showPassword);
		container.add(emailField);
		container.add(passwordField);
		container.add(phoneNumberField);
		container.add(lblEditInfo);

	}

	public void addActionEvent() {
		registerButton.addActionListener(this);
		showPassword.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
        DB dataBase = DB.getInstance();

		if (e.getSource() == registerButton) {
			String email = emailField.getText();
			String mobileNumber = phoneNumberField.getText();
			char[] password = passwordField.getPassword();

			if (!isValidPhoneNumber(mobileNumber)) {
				JOptionPane.showMessageDialog(btnNewButton, "Enter a valid mobile number (5-- --- ----).");
			}
			if (!isValidEmail(email)) {
				JOptionPane.showMessageDialog(btnNewButton, "Please enter a valid email.");
			}
			if (String.valueOf(password).length() < 6) {
				JOptionPane.showMessageDialog(btnNewButton, "Password must be longer than 6 characters");
			}
			if (!(!isValidEmail(email) || String.valueOf(password).length() < 6)) {
				PersonalInfo info = Dcs.currentUser.getPersonalInfo();

				dataBase.UpdateUserInfo(info.getIdentityNumber(), "email", emailField.getText());
				Dcs.currentUser.getPersonalInfo().setEmail(emailField.getText());

				dataBase.UpdateUserInfo(info.getIdentityNumber(), "phone_number", phoneNumberField.getText());
				Dcs.currentUser.getPersonalInfo().setPhoneNumber(phoneNumberField.getText());

				dataBase.UpdateUserInfo(info.getIdentityNumber(), "password", String.valueOf(password));
				Dcs.currentUser.getPersonalInfo().setPassword(String.valueOf(password));

				JOptionPane.showMessageDialog(btnNewButton, "Updated Succesfully");
				dispose();
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
