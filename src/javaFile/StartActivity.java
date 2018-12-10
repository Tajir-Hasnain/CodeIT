package javaFile;

import java.awt.Color;
import java.awt.event.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class StartActivity extends JFrame implements ActionListener {
	
	//constructor
	StartActivity() {
		JButton button = new JButton("Click");
		
		JLabel text = new JLabel("<html><font size = 20 , color = 'RED'>Welcome.Press the Button to start</font></html>");
		text.setBounds(650,350,900,200);
		button.setBounds(900,500,100,40);
		button.addActionListener(this);
		add(button);
		add(text);
		JMenuBar menuBar = new JMenuBar();
		JMenu menu1 = new JMenu("File");
		
		JMenuItem m1i1 = new JMenuItem("New");
		JMenuItem m1i2 = new JMenuItem("Open");
		JMenuItem m1i3 = new JMenuItem("Close");
		
		menu1.add(m1i1);
		menu1.add(m1i2);
		menu1.add(m1i3);
		
		
		menuBar.add(menu1);
		
		setJMenuBar(menuBar);
		
		setSize(2000,1100);
		setLayout(null);
		setVisible(true);
		getContentPane().setBackground(Color.DARK_GRAY);
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
