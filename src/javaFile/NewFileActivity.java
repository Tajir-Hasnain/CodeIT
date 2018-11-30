package javaFile;

import javax.swing.JFrame;
import javax.swing.*;

import java.awt.Color;
import java.awt.event.*;
import java.util.ArrayList;

public class NewFileActivity extends JFrame implements ActionListener {
	NewFileActivity() {
		setSize(400,500);
		JTextArea text = new JTextArea();
		text.setBounds(30, 40, 320, 350);
		add(text);
		JButton button = new JButton("Save");
		button.setBounds(260 ,410,120 ,30);
		add(button);
		
		//Auto Suggester
		
		AutoSuggestor suggest = new AutoSuggestor(text,this,null,Color.WHITE,Color.BLUE,Color.RED,0.75f) {
			@Override
			boolean wordTyped(String typeWord) {
				ArrayList<String> words = new ArrayList<>();
				words.add("map < int , int > ");
				words.add("vector < int , int > ");
				setDictionary();
			}
		};
		
		setLayout(null);
		setVisible(true);
	}
	public static void main(String[] args) {
		new NewFileActivity();
	}
}
