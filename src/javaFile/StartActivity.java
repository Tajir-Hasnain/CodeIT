package javaFile;

import java.awt.Color;

import java.awt.event.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class StartActivity extends JFrame implements ActionListener {
	@SuppressWarnings("unused")
	private JLabel imglabel;
	//constructor
	private ImageIcon img1;
	StartActivity() {
		img1 = new ImageIcon(getClass().getResource("notepad3.jpg"));
		imglabel = new JLabel(img1);
		imglabel.setBounds(0,0,1400,1051);
		add(imglabel);
		
		JButton button = new JButton("START");
		
		JLabel text = new JLabel("<html><font size = 20 , color = 'RED'>Welcome.Press the Button to start</font></html>");
		text.setBounds(650,350,900,200);
		button.setBounds(600,500,200,40);
		button.addActionListener(this);
		button.setBackground(Color.white);
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
		
		setBounds(300,50,1400,1051);
		setLayout(null);
		setVisible(true);
		getContentPane().setBackground(Color.DARK_GRAY);
	}
	
	//Start IndexActivity
	@Override
	public void actionPerformed(ActionEvent e) {
		this.dispose();
		Interface index = new Interface();
		index.setSize(2000,1100);
		index.setVisible(true);
	}
	
	public static void main(String[] args) {
		new StartActivity();
	}
}
