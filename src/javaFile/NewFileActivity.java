package javaFile;

import javax.swing.JFrame;
import javax.swing.*;

import java.awt.event.*;

public class NewFileActivity extends JFrame implements ActionListener {
	NewFileActivity() {
		setSize(400,500);
		JTextArea text = new JTextArea();
		text.setBounds(30, 40, 320, 350);
		add(text);
		JButton button = new JButton("Save");
		button.setBounds(260 ,410,120 ,30);
		add(button);
		setLayout(null);
		setVisible(true);
	}
	public static void main(String[] args) {
		new NewFileActivity();
	}
}
