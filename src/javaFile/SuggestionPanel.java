package javaFile;

import javax.swing.*;
import javax.swing.text.BadLocationException;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;

@SuppressWarnings("unused")
public class SuggestionPanel {
	private JPopupMenu popupmenu;
	private JList list;
	private String word;
	private JTextArea text;
	private final int insertPosition;
	
	//constructor
	SuggestionPanel(JTextArea text , int position , String word, Point location) {
		this.insertPosition = position;
		this.word = word;
		this.text = text;
		
		//reset
		popupmenu = new JPopupMenu();
		popupmenu.removeAll();
		popupmenu.setOpaque(false);
		popupmenu.setBorder(null);
		popupmenu.add(list = createSuggestionList(position,word) , BorderLayout.CENTER);
		popupmenu.show(text,location.x,text.getBaseline(0,0)+location.y));
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
                if (e.getClickCount() == 2) {
                    insertSelection();
                }
            }

			private boolean insertSelection() {
				if (list.getSelectedValue() != null) {
	                try {
	                    final String selectedSuggestion = ((String) list.getSelectedValue()).substring(word.length());
	                    text.getDocument().insertString(insertionPosition, selectedSuggestion, null);
	                    return true;
	                } catch (BadLocationException e1) {
	                    e1.printStackTrace();
	                }
	                hideSuggestion();
	            }
	            return false;
			}
        });
		
		return listLocal;
	}
	
	public void hide() {
		popupmenu.setVisible(false);
		popupmenu = null;
	}
}
