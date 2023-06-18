package Gui;

import javax.swing.*;

import dcs.DB;
import dcs.PersonalInfo;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreatePersonnelWindow extends JFrame implements ActionListener {

	private Container container = getContentPane();
	private JLabel userIDLabel = new JLabel("USER ID");
	private JLabel userPhoneNumberLabel = new JLabel("USER PHONE NUMBER");
	private JLabel lblCreatePersonnel = new JLabel("CREATE PERSONNEL");

	private JTextField userIDField = new JTextField();
	private JTextField userPhoneNumberField = new JTextField();

	private JButton assignButton = new JButton("ASSIGN");
	private JButton btnNewButton = new JButton("Ok");

	public CreatePersonnelWindow() {
		setLayoutManager();
		setLocationAndSize();
		addComponentsToContainer();
		addActionEvent();
		setFont();
		setColumns();
		this.setTitle("DCS | CREATE REQUEST");
		this.setVisible(true);
		this.setBounds(450, 190, 1014, 597);
		this.setResizable(false);
	}

	public void setLayoutManager() {
		container.setLayout(null);
	}

	public void setLocationAndSize() {
		lblCreatePersonnel.setBounds(350, 62, 500, 50);

		userIDLabel.setBounds(58, 162, 300, 43);
		userPhoneNumberLabel.setBounds(500, 169, 300, 29);

		userIDField.setBounds(214, 161, 228, 50);
		userPhoneNumberField.setBounds(707, 161, 228, 50);

		btnNewButton.setBounds(399, 437, 259, 74);
		assignButton.setBounds(399, 437, 259, 74);

	}

	public void setFont() {
		lblCreatePersonnel.setFont(new Font("Times New Roman", Font.PLAIN, 42));

		userIDLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		userIDField.setFont(new Font("Tahoma", Font.PLAIN, 32));
		userPhoneNumberLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		userPhoneNumberField.setFont(new Font("Tahoma", Font.PLAIN, 32));
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 22));
		assignButton.setFont(new Font("Tahoma", Font.PLAIN, 22));
	}

	public void setColumns() {
		userIDField.setColumns(10);
		userPhoneNumberField.setColumns(10);
	}

	public void addComponentsToContainer() {
		container.add(userIDLabel);
		container.add(userPhoneNumberLabel);
		container.add(assignButton);
		container.add(userIDField);
		container.add(userPhoneNumberField);
		container.add(lblCreatePersonnel);

	}

	public void addActionEvent() {
		assignButton.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
        DB dataBase = DB.getInstance();

		if (e.getSource() == assignButton) {
			if (dataBase.isPersonnelExist(userIDField.getText())) {
				JOptionPane.showMessageDialog(btnNewButton, "Personnel Is Exist");
			} else if (dataBase.userValidation(userIDField.getText(), userPhoneNumberField.getText())) {
				JOptionPane.showMessageDialog(btnNewButton, "Personnel is created. ID: " + userIDField.getText());
				PersonalInfo userInfo = dataBase.getPersonalInfo(userIDField.getText());

				dataBase.InsertPersonnel(userInfo.getFirstName(), userInfo.getLastName(), userInfo.getPhoneNumber(),
						userInfo.getIdentityNumber());
				dataBase.UpdateUserInfo(userInfo.getIdentityNumber(), "is_personnel", "true");
				dispose();
			} else {
				JOptionPane.showMessageDialog(btnNewButton, "ERROR");
			}
		}

	}

}
