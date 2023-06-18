package Gui;

import javax.swing.*;

import dcs.DB;
import dcs.Dcs;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CoordinationUnitMainMenuWindow extends JFrame implements ActionListener {

	private Container container = getContentPane();
	private JButton requestsBtn = new JButton("REQUESTS");
	private JButton personnelBtn = new JButton("PERSONNEL");
	private JButton logoutBtn = new JButton("LOGOUT");

	public CoordinationUnitMainMenuWindow() {
		setLayoutManager();
		setLocationAndSize();
		addComponentsToContainer();
		addActionEvent();
		setFont();
		this.setTitle("DCS | MAIN MENU");
		this.setVisible(true);
		this.setBounds(450, 190, 1014, 597);
		this.setResizable(false);
		System.out.println("Coordination Unit login");
	}

	public void setLayoutManager() {
		container.setLayout(null);
	}

	public void setLocationAndSize() {
		requestsBtn.setBounds(375, 100, 300, 74);
		personnelBtn.setBounds(375, 200, 300, 74);
		logoutBtn.setBounds(375, 300, 300, 74);
	}

	public void setFont() {
		requestsBtn.setFont(new Font("Tahoma", Font.PLAIN, 22));
		personnelBtn.setFont(new Font("Tahoma", Font.PLAIN, 22));
		logoutBtn.setFont(new Font("Tahoma", Font.PLAIN, 22));
	}

	public void addComponentsToContainer() {
		container.add(requestsBtn);
		container.add(personnelBtn);
		container.add(logoutBtn);

	}

	public void addActionEvent() {
		requestsBtn.addActionListener(this);
		personnelBtn.addActionListener(this);
		logoutBtn.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == requestsBtn) {
			dispose();
			CoordinationUnitRequestsWindow coordinationUnitRequestsWindow = new CoordinationUnitRequestsWindow();
		}
		if (e.getSource() == personnelBtn) {
			dispose();
			CoordinationUnitPersonnelWindow coordinationUnitPersonnelWindow = new CoordinationUnitPersonnelWindow();
		}

		if (e.getSource() == logoutBtn) {
			dispose();
			Dcs dcs = new Dcs();
		}

	}
}
