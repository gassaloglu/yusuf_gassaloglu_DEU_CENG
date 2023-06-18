package Gui;

import javax.swing.*;

import dcs.DB;
import dcs.Dcs;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PersonnelMainMenuWindow extends JFrame implements ActionListener {

	private Container container = getContentPane();
	private JButton tasksBtn = new JButton("TASKS");
	private JButton logoutBtn = new JButton("LOGOUT");

	public PersonnelMainMenuWindow() {
		setLayoutManager();
		setLocationAndSize();
		addComponentsToContainer();
		addActionEvent();
		setFont();
		this.setTitle("DCS | MAIN MENU");
		this.setVisible(true);
		this.setBounds(450, 190, 1014, 597);
		this.setResizable(false);
		System.out.println("Personnel login");
	}

	public void setLayoutManager() {
		container.setLayout(null);
	}

	public void setLocationAndSize() {
		tasksBtn.setBounds(375, 150, 300, 74);
		logoutBtn.setBounds(375, 250, 300, 74);
	}

	public void setFont() {
		tasksBtn.setFont(new Font("Tahoma", Font.PLAIN, 22));
		logoutBtn.setFont(new Font("Tahoma", Font.PLAIN, 22));
	}

	public void addComponentsToContainer() {
		container.add(tasksBtn);
		container.add(logoutBtn);

	}

	public void addActionEvent() {
		tasksBtn.addActionListener(this);
		logoutBtn.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == tasksBtn) {
			dispose();
			PersonnelTasksWindow personnelTasksWindow = new PersonnelTasksWindow();
		}
		if (e.getSource() == logoutBtn) {
			dispose();
			Dcs dcs = new Dcs();
		}

	}
}
