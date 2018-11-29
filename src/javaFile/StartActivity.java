package javaFile;

import java.awt.event.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class StartActivity extends JFrame implements ActionListener {
	
	//constructor
	StartActivity() {
		JButton button = new JButton("Click");
		
		JLabel text = new JLabel("Welcome.Press the Button to start");
		text.setBounds(100,80,200,20);
		button.setBounds(130,100,100,40);
		button.addActionListener(this);
		add(button);
		add(text);
		setSize(400,500);
		setLayout(null);
		setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		this.dispose();
		IndexActivity index = new IndexActivity();
		index.setVisible(true);
	}
	
	public static void main(String[] args) {
		new StartActivity();
	}
}
