package javaFile;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;

import com.sun.org.slf4j.internal.Logger;

import javaFile.Dictionary;


@SuppressWarnings({ "unused", "serial" })
public class Console extends JFrame implements KeyListener {
	
	//Necessary Variables
	JTextArea textArea;
	JButton saveButton = new JButton("Save");
	private Font f;
	
	@SuppressWarnings("unused")
	public class SuggestionPanel {
		private JPopupMenu popupMenu;
		private JList list;
		private String word;
		private final int insertPosition;
		
		//constructor
		public SuggestionPanel(JTextArea textarea, int position, String subWord, Point location) {
            this.insertPosition = position;
            this.word = subWord;
            popupMenu = new JPopupMenu();
            popupMenu.removeAll();
            popupMenu.setOpaque(false);
            popupMenu.setBorder(null);
            popupMenu.add(list = createSuggestionList(position, subWord), BorderLayout.CENTER);
            popupMenu.show(textarea, location.x, textarea.getBaseline(0, 0) + location.y);
        }

		@SuppressWarnings({ "rawtypes", "unchecked" })
		private JList createSuggestionList(int position, String word2) {
			
			//Generate available words suggested for word2
			
			Dictionary dic = new Dictionary();
			ArrayList < String > data = new ArrayList<String>(1000);
			
			for(int idx = 0; idx < dic.data.size(); ++idx) {
				String s = dic.data.get(idx);
				boolean isOk = true;
				for(int i = 0; i < word2.length(); ++i) {
					if(word2.charAt(i) == s.charAt(i))
						continue;
					else {
						isOk = false;
						break;
					}
				}
				if(isOk) {
					data.add(s);
				}
			}
			
			JList listLocal = new JList(data.toArray());
			listLocal.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
	        listLocal.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	        listLocal.setSelectedIndex(0);
	        listLocal.addMouseListener(new MouseAdapter() {
	            @Override
	            public void mouseClicked(MouseEvent e) {
	                if (e.getClickCount() == 1) {
	                    insertSelection();
	                }
	            }
	        });
			
			return listLocal;
		}
		
		public void hide() {
			popupMenu.setVisible(false);
			if(suggestion == this)
				suggestion = null;
		}

		public boolean insertSelection() {
			if (list.getSelectedValue() != null) {
                try {
                    final String selectedSuggestion = ((String) list.getSelectedValue()).substring(word.length());
                    textArea.getDocument().insertString(insertPosition, selectedSuggestion, null);
                    textArea.setCaretPosition(textArea.getText().length());
                    return true;
                } catch (BadLocationException e1) {
                    e1.printStackTrace();
                }
                hideSuggestion();
            }
            return false;
		}
		public void moveUp() {
            int index = Math.min(list.getSelectedIndex() - 1, 0);
            selectIndex(index);
        }

        public void moveDown() {
            int index = Math.min(list.getSelectedIndex() + 1, list.getModel().getSize() - 1);
            selectIndex(index);
        }

        private void selectIndex(int index) {
            final int position = textArea.getCaretPosition();
            list.setSelectedIndex(index);
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    textArea.setCaretPosition(position);
                };
            });
        }

	}
	//End of SuggestionPanel class;
	
	private SuggestionPanel suggestion;
	private String file;
	
	private void hideSuggestion() {
		if(suggestion != null)
			suggestion.hide();
	}
	
	
	protected void CallShowSuggestion() {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				try {
					showSuggestion();
				} catch (BadLocationException e) {
					e.printStackTrace();
				}
			}
			
		});
	}
	
	@SuppressWarnings("deprecation")
	protected void showSuggestion() throws BadLocationException {
		final int position = textArea.getCaretPosition();
		Point location;
		
		hideSuggestion();
		location = textArea.getLocation();
        String text = textArea.getText();
        int start = Math.max(0, position - 1);
        while (start > 0) {
            if (!Character.isWhitespace(text.charAt(start))) {
                start--;
            } else {
                start++;
                break;
            }
        }
        if (start > position) {
            return;
        }
        System.out.println(start + " " + position);
        final String word = text.substring(start, position);
        if (word.length() < 0) {
            return;
        }
        suggestion = new SuggestionPanel(textArea, position, word, location);
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                textArea.requestFocusInWindow();
            }
        });
	}
	
	//Constructor for the class
	public Console() {
		textArea = new JTextArea();
		textArea.setSize(1800,900);
		textArea.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY , 1));
		f= new Font("Ariell",Font.PLAIN,16);
		textArea.setFont(f);
		textArea.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
//				System.out.println(arg0.getKeyChar());
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				if(Character.isLetterOrDigit(arg0.getKeyChar())) {
					System.out.println("Key Released : " + arg0.getKeyChar());
					CallShowSuggestion();
				}
				else if(Character.isWhitespace(arg0.getKeyChar())) 
					hideSuggestion();
				else if(arg0.getKeyCode() == KeyEvent.VK_DOWN && suggestion != null)
					suggestion.moveDown();
				else if(arg0.getKeyCode() == KeyEvent.VK_UP && suggestion != null)
					suggestion.moveUp();
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				if(arg0.getKeyChar() == KeyEvent.VK_ENTER) {
                    if (suggestion != null) {
                        if (suggestion.insertSelection()) {
                            arg0.consume();
                            final int position = textArea.getCaretPosition();
                            SwingUtilities.invokeLater(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        textArea.getDocument().remove(position - 1, 1);
                                    } catch (BadLocationException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                    }
                }
			}

		});
		
		
		saveButton.setBounds(1600,920,120,40);
		saveButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				saveAs();
			}
			
		});
		add(saveButton);
		
		JMenuBar menuBar = new JMenuBar();
		JMenu menu1 = new JMenu("File");
		
		JMenuItem m1i1 = new JMenuItem("New");
		JMenuItem m1i2 = new JMenuItem("Open");
		JMenuItem m1i3 = new JMenuItem("Close");
		
		menu1.add(m1i1);
		menu1.add(m1i2);
		menu1.add(m1i3);
		
		m1i2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				openFile();
			}
			
		});
		
		m1i3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				textArea.setText("");
			}
			
		});
		
		menuBar.add(menu1);
		
		setJMenuBar(menuBar);
		add(textArea);
		setSize(2000,1100);
		setLayout(null);
		setVisible(true);
		getContentPane().setBackground(Color.DARK_GRAY);
	}
	Console(String name, String inst, String prof) {
		String s = "/*\n*Author : ";
		s += name;
		s += '\n';
		s += "*Institution : ";
		s += inst;
		s += '\n';
		s += "*Profession : ";
		s += prof;
		s += "\n*/\n\n";
		
		textArea = new JTextArea();
		textArea.setText(s);
		textArea.setSize(1800,900);
		textArea.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY , 1));
		f= new Font("Ariell",Font.PLAIN,16);
		textArea.setFont(f);
		textArea.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
//				System.out.println(arg0.getKeyChar());
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				if(Character.isLetterOrDigit(arg0.getKeyChar())) {
					System.out.println("Key Released : " + arg0.getKeyChar());
					CallShowSuggestion();
				}
				else if(Character.isWhitespace(arg0.getKeyChar())) 
					hideSuggestion();
				else if(arg0.getKeyCode() == KeyEvent.VK_DOWN && suggestion != null)
					suggestion.moveDown();
				else if(arg0.getKeyCode() == KeyEvent.VK_UP && suggestion != null)
					suggestion.moveUp();
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				if(arg0.getKeyChar() == KeyEvent.VK_ENTER) {
                    if (suggestion != null) {
                        if (suggestion.insertSelection()) {
                            arg0.consume();
                            final int position = textArea.getCaretPosition();
                            SwingUtilities.invokeLater(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        textArea.getDocument().remove(position - 1, 1);
                                    } catch (BadLocationException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                    }
                }
			}

		});
		
		
		saveButton.setBounds(1600,920,120,40);
		saveButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				saveAs();
			}
			
		});
		add(saveButton);
		
		JMenuBar menuBar = new JMenuBar();
		JMenu menu1 = new JMenu("File");
		
		JMenuItem m1i1 = new JMenuItem("New");
		JMenuItem m1i2 = new JMenuItem("Open");
		JMenuItem m1i3 = new JMenuItem("Close");
		
		menu1.add(m1i1);
		menu1.add(m1i2);
		menu1.add(m1i3);
		
		m1i2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				openFile();
			}
			
		});
		
		m1i3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				textArea.setText("");
			}
			
		});
		
		menuBar.add(menu1);
		
		setJMenuBar(menuBar);
		add(textArea);
		setSize(2000,1100);
		setLayout(null);
		setVisible(true);
		getContentPane().setBackground(Color.DARK_GRAY);
		
	}
	
	public void saveAs() {
	      FileNameExtensionFilter extensionFilter = new FileNameExtensionFilter("C/C++ File", "cpp");
	      final JFileChooser saveAsFileChooser = new JFileChooser();
	      saveAsFileChooser.setApproveButtonText("Save");
	      saveAsFileChooser.setFileFilter(extensionFilter);
	      int actionDialog = saveAsFileChooser.showOpenDialog(this);
	      if (actionDialog != JFileChooser.APPROVE_OPTION) {
	         return;
	      }

	      // !! File fileName = new File(SaveAs.getSelectedFile() + ".txt");
	      File file = saveAsFileChooser.getSelectedFile();
	      if (!file.getName().endsWith(".cpp")) {
	         file = new File(file.getAbsolutePath() + ".cpp");
	      }

	      BufferedWriter outFile = null;
	      try {
	         outFile = new BufferedWriter(new FileWriter(file));

	         textArea.write(outFile);

	      } catch (IOException ex) {
	         ex.printStackTrace();
	      } finally {
	         if (outFile != null) {
	            try {
	               outFile.close();
	            } catch (IOException e) {}
	         }
	      }
	  }
	
	public void openFile() {
		JFileChooser jf = new JFileChooser();
	     final JEditorPane document = new JEditorPane();
	    int returnval=jf.showDialog(this, null);
	    File file = null;
	    if(returnval == JFileChooser.APPROVE_OPTION)     
	     file = jf.getSelectedFile(); 
	    String str ;
	    try {
	        byte bt[]= Files.readAllBytes(file.toPath());   
	        str=new String(bt,"UTF-8");
	        textArea.setText(str);
	    } catch (IOException ex) {
	    	//
	    }
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new Console();
				}
				catch(Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
