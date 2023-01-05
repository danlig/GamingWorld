package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class MainFrame extends JFrame {
	
	public class SigningPanel {
		
	}
	
	public MainFrame() {
		this.setSize(500, 500);
		
		JButton button = new JButton("CambiaFinestra");
		JButton button2 = new JButton("CambiaFinestra");
		
		login = new JPanel();
		login.add(new JLabel("Login"));
		login.add(button);
		
		currentPanel = login;
		this.add(currentPanel);
		
		registration = new JPanel();
		registration.add(new JLabel("Registration"));
		registration.add(button2);
		
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				loadPanel(registration);
			}
		});
		button2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				loadPanel(login);
			}
		});
		
		this.setVisible(true);
	}
	
	public void loadPanel(JPanel panel) {
		if(currentPanel != null)
			this.remove(currentPanel);
		currentPanel = panel;
		this.add(panel);
		
		this.invalidate();
		this.validate();
		this.repaint();
	}
	
	private JPanel currentPanel;
	private JPanel login;
	private JPanel registration;

	
}
