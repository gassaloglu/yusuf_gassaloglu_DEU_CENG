package Gui;

import javax.swing.*;

import dcs.DB;
import dcs.Dcs;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserMainMenuWindow extends JFrame implements ActionListener {

	private Container container = getContentPane();
	private JButton requestsBtn = new JButton("REQUESTS");
	private JButton editPersonalInfoBtn = new JButton("EDIT PERSONAL INFO");
	private JButton logoutBtn = new JButton("LOGOUT");

	public UserMainMenuWindow() {
		setLayoutManager();
		setLocationAndSize();
		addComponentsToContainer();
		addActionEvent();
		setFont();
		this.setTitle("DCS | MAIN MENU");
		this.setVisible(true);
		this.setBounds(450, 190, 1014, 597);
		this.setResizable(false);
		System.out.println("User login");
	}

	public void setLayoutManager() {
		container.setLayout(null);
	}

	public void setLocationAndSize() {
		requestsBtn.setBounds(375, 100, 300, 74);
		editPersonalInfoBtn.setBounds(375, 200, 300, 74);
		logoutBtn.setBounds(375, 300, 300, 74);
	}

	public void setFont() {
		requestsBtn.setFont(new Font("Tahoma", Font.PLAIN, 22));
		editPersonalInfoBtn.setFont(new Font("Tahoma", Font.PLAIN, 22));
		logoutBtn.setFont(new Font("Tahoma", Font.PLAIN, 22));
	}

	public void addComponentsToContainer() {
		container.add(requestsBtn);
		container.add(editPersonalInfoBtn);
		container.add(logoutBtn);

	}

	public void addActionEvent() {
		requestsBtn.addActionListener(this);
		editPersonalInfoBtn.addActionListener(this);
		logoutBtn.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == requestsBtn) {
			dispose();
			UserRequestsWindow userRequestsWindow = new UserRequestsWindow();
		}
		if (e.getSource() == editPersonalInfoBtn) {
			UserEditPersonelInfoWindow userEditPersonelInfoWindow = new UserEditPersonelInfoWindow();
		}

		if (e.getSource() == logoutBtn) {
			dispose();
			Dcs dcs = new Dcs();
		}

	}
}
