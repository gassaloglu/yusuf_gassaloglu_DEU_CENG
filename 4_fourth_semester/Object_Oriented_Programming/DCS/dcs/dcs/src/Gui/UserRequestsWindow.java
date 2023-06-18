package Gui;

import javax.swing.*;

import dcs.DB;
import dcs.Dcs;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserRequestsWindow extends JFrame {
	private JPanel buttonPanel;
	private JButton createNewRequestBtn;
	private JButton goBackBtn;
	private JScrollPane scrollPane;
	private JList<String> requestList;

	public void userRequestsWindow() {
        DB dataBase = DB.getInstance();
		requestList = new JList<>(dataBase.printRequestsList(Dcs.currentUser.getPersonalInfo().getIdentityNumber()));
		scrollPane = new JScrollPane(requestList);
		buttonPanel = new JPanel();
		createNewRequestBtn = new JButton("CREATE NEW REQUEST");
		goBackBtn = new JButton("GO BACK");

		setTitle("DCS | REQUESTS");
		setLayout(new BorderLayout());

		buttonPanel.setLayout(new FlowLayout());

		createNewRequestBtn.setPreferredSize(new Dimension(300, 74));
		goBackBtn.setPreferredSize(new Dimension(300, 74));

		createNewRequestBtn.setFont(new Font("Tahoma", Font.PLAIN, 22));
		goBackBtn.setFont(new Font("Tahoma", Font.PLAIN, 22));
		requestList.setFont(new Font("Tahoma", Font.PLAIN, 35));

		add(scrollPane, BorderLayout.CENTER);
		buttonPanel.add(createNewRequestBtn);
		buttonPanel.add(goBackBtn);
		add(buttonPanel, BorderLayout.SOUTH);

		setBounds(450, 190, 1014, 597);
		setVisible(true);
		setResizable(false);

		goBackBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				UserMainMenuWindow userMainMenuWindow = new UserMainMenuWindow();
			}
		});

		createNewRequestBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CreateRequestWindow createRequestWindow = new CreateRequestWindow();
			}
		});

	}

	public UserRequestsWindow() {
		userRequestsWindow();
	}

}
