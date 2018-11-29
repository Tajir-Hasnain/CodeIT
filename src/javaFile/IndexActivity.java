package javaFile;

import java.awt.GraphicsConfiguration;
import javax.swing.*;
import java.awt.HeadlessException;
import java.awt.event.*;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class IndexActivity extends JFrame implements ActionListener {

	IndexActivity() {
		JFrame mainframe = this;
		JButton button = new JButton("Add new");
		button.setBounds(270,350,120,40);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainframe.dispose();
				NewFileActivity newfile = new NewFileActivity();
				newfile.setVisible(true);
			}
		});
		add(button);
		setSize(400,500);
		setLayout(null);
		setVisible(true);
	}
	public static void main(String[] args) {
		new IndexActivity();
	}
}
