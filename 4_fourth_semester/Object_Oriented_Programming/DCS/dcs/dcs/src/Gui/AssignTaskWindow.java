package Gui;

import javax.swing.*;

import dcs.DB;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AssignTaskWindow extends JFrame implements ActionListener {

	private Container container = getContentPane();
	private JLabel requestIDLabel = new JLabel("REQUEST ID");
	private JLabel personnelIDLabel = new JLabel("PERSONNEL ID");
	private JLabel lblCreateRequest = new JLabel("ASSIGN TASK");

	private JTextField requestIDField = new JTextField();
	private JTextField personnelIDField = new JTextField();

	private JButton assignButton = new JButton("ASSIGN");
	private JButton btnNewButton = new JButton("Ok");

	public AssignTaskWindow() {
		setLayoutManager();
		setLocationAndSize();
		addComponentsToContainer();
		addActionEvent();
		setFont();
		setColumns();
		this.setTitle("DCS | ASSIGN TASK");
		this.setVisible(true);
		this.setBounds(450, 190, 1014, 597);
		this.setResizable(false);
	}

	public void setLayoutManager() {
		container.setLayout(null);
	}

	public void setLocationAndSize() {
		lblCreateRequest.setBounds(362, 62, 325, 50);

		requestIDLabel.setBounds(58, 162, 150, 43);
		personnelIDLabel.setBounds(551, 169, 150, 29);

		requestIDField.setBounds(214, 161, 228, 50);
		personnelIDField.setBounds(707, 161, 228, 50);

		btnNewButton.setBounds(399, 437, 259, 74);
		assignButton.setBounds(399, 437, 259, 74);

	}

	public void setFont() {
		lblCreateRequest.setFont(new Font("Times New Roman", Font.PLAIN, 42));

		requestIDLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		requestIDField.setFont(new Font("Tahoma", Font.PLAIN, 32));
		personnelIDLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		personnelIDField.setFont(new Font("Tahoma", Font.PLAIN, 32));
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 22));
		assignButton.setFont(new Font("Tahoma", Font.PLAIN, 22));
	}

	public void setColumns() {
		requestIDField.setColumns(10);
		personnelIDField.setColumns(10);
	}

	public void addComponentsToContainer() {
		container.add(requestIDLabel);
		container.add(personnelIDLabel);
		container.add(assignButton);
		container.add(requestIDField);
		container.add(personnelIDField);
		container.add(lblCreateRequest);
	}

	public void addActionEvent() {
		assignButton.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
        DB dataBase = DB.getInstance();

		if (e.getSource() == assignButton) {
			if (!dataBase.isUserExist(personnelIDField.getText())) {
				JOptionPane.showMessageDialog(btnNewButton, "Personnel Is Not Exist");
			} else if (!dataBase.isRequestExist(requestIDField.getText())) {
				JOptionPane.showMessageDialog(btnNewButton, "Request Is Not Exist");
			} else if (dataBase.isTaskAssigned(requestIDField.getText())) {
				JOptionPane.showMessageDialog(btnNewButton, "The Task Is Already Assigned");
			} else if (dataBase.isUserExist(personnelIDField.getText())
					&& dataBase.isRequestExist(requestIDField.getText())
					&& !dataBase.isTaskAssigned(requestIDField.getText())) {
				JOptionPane.showMessageDialog(btnNewButton, "The Task Is Assigned");

				dataBase.UpdatePersonnelInfo(personnelIDField.getText(), "task_id", requestIDField.getText());
				dataBase.UpdateRequesInfo(requestIDField.getText(), "status", "PENDING");
				dataBase.UpdateRequesInfo(requestIDField.getText(), "personnel_id", personnelIDField.getText());

				dispose();
			} else {
				JOptionPane.showMessageDialog(btnNewButton, "ERROR");
			}
		}
	}
}
