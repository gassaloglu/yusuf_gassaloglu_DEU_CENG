package Gui;

import javax.swing.*;

import dcs.DB;
import dcs.Dcs;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class UpdateTaskWindow extends JFrame implements ActionListener {

	private Container container = getContentPane();
	private JLabel taskIDLabel = new JLabel("TASK ID");
	private JLabel setStatusLabel = new JLabel("SET STATUS");
	private JLabel lblUpdateTask = new JLabel("UPDATE TASK");

	private JTextField taskIDField = new JTextField();
	private JTextField setStatusField = new JTextField();

	private JButton updateButton = new JButton("UPDATE");
	private JButton btnNewButton = new JButton("Ok");

	public UpdateTaskWindow() {
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
		lblUpdateTask.setBounds(362, 62, 325, 50);

		taskIDLabel.setBounds(58, 162, 150, 43);
		setStatusLabel.setBounds(551, 169, 150, 29);

		taskIDField.setBounds(214, 161, 228, 50);
		setStatusField.setBounds(707, 161, 228, 50);

		btnNewButton.setBounds(399, 437, 259, 74);
		updateButton.setBounds(399, 437, 259, 74);

	}

	public void setFont() {
		lblUpdateTask.setFont(new Font("Times New Roman", Font.PLAIN, 42));

		taskIDLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		taskIDField.setFont(new Font("Tahoma", Font.PLAIN, 32));
		setStatusLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		setStatusField.setFont(new Font("Tahoma", Font.PLAIN, 32));
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 22));
		updateButton.setFont(new Font("Tahoma", Font.PLAIN, 22));
	}

	public void setColumns() {
		taskIDField.setColumns(10);
		setStatusField.setColumns(10);
	}

	public void addComponentsToContainer() {
		container.add(taskIDLabel);
		container.add(setStatusLabel);
		container.add(updateButton);
		container.add(taskIDField);
		container.add(setStatusField);
		container.add(lblUpdateTask);

	}

	public void addActionEvent() {
		updateButton.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
        DB dataBase = DB.getInstance();

		if (e.getSource() == updateButton) {
			if (!dataBase.isValidTask(Dcs.currentUser.getPersonalInfo().getIdentityNumber(), taskIDField.getText())) {
				JOptionPane.showMessageDialog(btnNewButton, "Task Is Not Exist");
			} else if (!(setStatusField.getText().equalsIgnoreCase("FAILED")
					|| setStatusField.getText().equalsIgnoreCase("DONE"))) {
				JOptionPane.showMessageDialog(btnNewButton, "Invalid Status");
			} else if (dataBase.isRequestExist(taskIDField.getText())
					&& (setStatusField.getText().equalsIgnoreCase("FAILED")
							|| setStatusField.getText().equalsIgnoreCase("DONE"))) {
				JOptionPane.showMessageDialog(btnNewButton, "Task Is Updated");
				dispose();

				dataBase.UpdatePersonnelInfo(Dcs.currentUser.getPersonalInfo().getIdentityNumber(), "task_id", "NaN");
				dataBase.UpdateRequesInfo(taskIDField.getText(), "status", setStatusField.getText());
				PersonnelTasksWindow personnelTasksWindow = new PersonnelTasksWindow();
			} else {
				JOptionPane.showMessageDialog(btnNewButton, "ERROR");
			}
		}

	}

}
