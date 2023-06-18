package Gui;

import javax.swing.*;

import dcs.DB;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CoordinationUnitPersonnelWindow extends JFrame {
	private JPanel buttonPanel;
	private JButton createPersonnelBtn;
	private JButton goBackBtn;
	private JScrollPane scrollPane;
	private JList<String> requestList;

	public void coordinationUnitRequestsWindow() {
        DB dataBase = DB.getInstance();

		requestList = new JList<>(dataBase.printAllFreePersonnel());
		scrollPane = new JScrollPane(requestList);
		buttonPanel = new JPanel();
		createPersonnelBtn = new JButton("CREATE PERSONNEL");
		goBackBtn = new JButton("GO BACK");

		setTitle("DCS | PERSONNEL");
		setLayout(new BorderLayout());

		buttonPanel.setLayout(new FlowLayout());

		createPersonnelBtn.setPreferredSize(new Dimension(300, 74));
		goBackBtn.setPreferredSize(new Dimension(300, 74));

		createPersonnelBtn.setFont(new Font("Tahoma", Font.PLAIN, 22));
		goBackBtn.setFont(new Font("Tahoma", Font.PLAIN, 22));
		requestList.setFont(new Font("Tahoma", Font.PLAIN, 35));

		add(scrollPane, BorderLayout.CENTER);
		buttonPanel.add(createPersonnelBtn);
		buttonPanel.add(goBackBtn);
		add(buttonPanel, BorderLayout.SOUTH);

		setBounds(450, 190, 1014, 597);
		setVisible(true);
		setResizable(false);

		goBackBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				CoordinationUnitMainMenuWindow coordinationUnitMainMenuWindow = new CoordinationUnitMainMenuWindow();
			}
		});

		createPersonnelBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CreatePersonnelWindow createPersonnelWindow = new CreatePersonnelWindow();

			}
		});

	}

	public CoordinationUnitPersonnelWindow() {
		coordinationUnitRequestsWindow();
	}

}
