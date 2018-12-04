package javaFile;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import javax.swing.*;
import javax.swing.text.BadLocationException;

import javaFile.Dictionary;


@SuppressWarnings({ "unused", "serial" })
public class Console extends JFrame implements KeyListener {
	
	//Necessary Variables
	JTextArea textArea;
	
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
	Console() {
		textArea = new JTextArea();
		textArea.setSize(300,400);
		textArea.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY , 1));
		
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
		
		add(textArea);
		setSize(400,500);
		setLayout(null);
		setVisible(true);
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
