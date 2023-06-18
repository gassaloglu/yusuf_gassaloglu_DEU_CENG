package Gui;

import javax.swing.*;

import dcs.DB;
import dcs.Dcs;
import dcs.Location;
import dcs.Request;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateRequestWindow extends JFrame implements ActionListener {

	private Container container = getContentPane();
	private JLabel longitudeLabel = new JLabel("LONGITUDE");
	private JLabel latitudeLabel = new JLabel("LATITUDE");
	private JLabel descriptionLabel = new JLabel("DESCRIPTION");
	private JLabel lblCreateRequest = new JLabel("Create Request");

	private JTextField longitudeField = new JTextField();
	private JTextField latitudeField = new JTextField();
	private JTextField descriptionField = new JTextField();

	private JButton createButton = new JButton("CREATE");
	private JButton btnNewButton = new JButton("Ok");

	public static Request request = new Request("", new Location(0, 0), "", "", "");

	private boolean isDouble(String str) {
		try {
			Double.parseDouble(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public CreateRequestWindow() {
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
		lblCreateRequest.setBounds(362, 62, 325, 50);

		longitudeLabel.setBounds(58, 162, 150, 43);
		latitudeLabel.setBounds(551, 169, 150, 29);
		descriptionLabel.setBounds(58, 262, 150, 29);

		longitudeField.setBounds(214, 161, 228, 50);
		latitudeField.setBounds(707, 161, 228, 50);
		descriptionField.setBounds(214, 253, 720, 50);

		btnNewButton.setBounds(399, 437, 259, 74);
		createButton.setBounds(399, 437, 259, 74);

	}

	public void setFont() {
		lblCreateRequest.setFont(new Font("Times New Roman", Font.PLAIN, 42));

		longitudeLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		longitudeField.setFont(new Font("Tahoma", Font.PLAIN, 32));
		latitudeLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		latitudeField.setFont(new Font("Tahoma", Font.PLAIN, 32));
		descriptionLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		descriptionField.setFont(new Font("Tahoma", Font.PLAIN, 32));
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 22));
		createButton.setFont(new Font("Tahoma", Font.PLAIN, 22));
	}

	public void setColumns() {
		longitudeField.setColumns(10);
		latitudeField.setColumns(10);
		descriptionField.setColumns(10);
	}

	public void addComponentsToContainer() {
		container.add(longitudeLabel);
		container.add(latitudeLabel);
		container.add(descriptionLabel);
		container.add(createButton);
		container.add(longitudeField);
		container.add(latitudeField);
		container.add(descriptionField);
		container.add(lblCreateRequest);

	}

	public void addActionEvent() {
		createButton.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
        DB dataBase = DB.getInstance();

		if (e.getSource() == createButton) {
			if (isDouble(longitudeField.getText()) && isDouble(latitudeField.getText())
					&& !descriptionField.getText().isBlank()
					&& dataBase.InsertRequest(descriptionField.getText(),
							(latitudeField.getText() + "," + longitudeField.getText()), "WAITING",
							Dcs.currentUser.getPersonalInfo().getIdentityNumber(), "NaN")) {
				JOptionPane.showMessageDialog(btnNewButton, "Request Created Succesfully");
				dispose();

				request.setDescription(descriptionField.getText());
				Location location = new Location(Double.parseDouble(latitudeField.getText()),
						Double.parseDouble(longitudeField.getText()));
				request.setLocation(location);
				request.setStatus("WAITING");
				request.setOwnerID(Dcs.currentUser.getPersonalInfo().getIdentityNumber());
				request.setPersonnelID("NaN");

			} else if (!isDouble(longitudeField.getText()) || !isDouble(latitudeField.getText())) {
				JOptionPane.showMessageDialog(btnNewButton, "Invalid Latitude or Longitude");
			} else if (descriptionField.getText().isBlank()) {
				JOptionPane.showMessageDialog(btnNewButton, "Description Cannot be Empty");
			} else if (longitudeField.getText().isBlank() || latitudeField.getText().isBlank()) {
				JOptionPane.showMessageDialog(btnNewButton, "Location Cannot be Empty");
			}

			else {
				JOptionPane.showMessageDialog(btnNewButton, "ERROR");
			}

		}

	}

}
