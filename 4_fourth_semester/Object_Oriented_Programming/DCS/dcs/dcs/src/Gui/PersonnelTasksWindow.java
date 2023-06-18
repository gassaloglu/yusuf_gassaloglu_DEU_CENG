package Gui;

import javax.swing.*;

import dcs.DB;
import dcs.Dcs;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class PersonnelTasksWindow extends JFrame {

	private JPanel buttonPanel;
	private JButton updateTaskBtn;
	private JButton goBackBtn;
	private JScrollPane scrollPane;
	private JList<String> tasksList;

	public void personnelTasksWindow() {
        DB dataBase = DB.getInstance();

		tasksList = new JList<>(dataBase.printAllTasks(Dcs.currentUser.getPersonalInfo().getIdentityNumber()));
		scrollPane = new JScrollPane(tasksList);
		buttonPanel = new JPanel();
		updateTaskBtn = new JButton("UPDATE TASK");
		goBackBtn = new JButton("GO BACK");

		setTitle("DCS | PERSONNEL");
		setLayout(new BorderLayout());

		buttonPanel.setLayout(new FlowLayout());

		updateTaskBtn.setPreferredSize(new Dimension(300, 74));
		goBackBtn.setPreferredSize(new Dimension(300, 74));

		updateTaskBtn.setFont(new Font("Tahoma", Font.PLAIN, 22));
		goBackBtn.setFont(new Font("Tahoma", Font.PLAIN, 22));
		tasksList.setFont(new Font("Tahoma", Font.PLAIN, 35));

		add(scrollPane, BorderLayout.CENTER);
		buttonPanel.add(updateTaskBtn);
		buttonPanel.add(goBackBtn);
		add(buttonPanel, BorderLayout.SOUTH);

		setBounds(450, 190, 1014, 597);
		setVisible(true);
		setResizable(false);

		goBackBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				PersonnelMainMenuWindow personnelMainMenuWindow = new PersonnelMainMenuWindow();
			}
		});

		updateTaskBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				UpdateTaskWindow updateTaskWindow = new UpdateTaskWindow();

			}
		});

	}

	public PersonnelTasksWindow() {
		personnelTasksWindow();
	}

}
