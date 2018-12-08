package javaFile;

import java.awt.event.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class StartActivity extends JFrame implements ActionListener {
	
	//constructor
	StartActivity() {
		JButton button = new JButton("Click");
		
		JLabel text = new JLabel("Welcome.Press the Button to start");
		text.setBounds(870,470,200,20);
		button.setBounds(900,500,100,40);
		button.addActionListener(this);
		add(button);
		add(text);
		setSize(2000,1100);
		setLayout(null);
		setVisible(true);
	}
	
	//Start IndexActivity
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
